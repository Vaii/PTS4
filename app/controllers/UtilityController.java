package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dal.contexts.CategoryContext;
import dal.contexts.DateTimeMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.CategoryRepository;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
import dal.repositories.UserRepository;
import models.storage.DateTime;
import models.storage.Category;
import models.storage.User;
import models.util.DateConverter;
import models.util.OverlapChecker;
import models.view.ViewDate;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.example.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UtilityController extends Controller {

    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    private CategoryRepository catRepo = new CategoryRepository(new CategoryContext("Category"));

    List<String> examples = new ArrayList<>();

    public Result checkEmail(String email) {
        if(userRepo.getUser(email) != null) {
            return ok("user_exists");
        } else {
            return ok("user_nonexists");
        }
    }

    public Result checkEmailExists(String userId, String email) {
        User user = userRepo.getUser(email);

        // Check if we already have a user.
        if(user != null) {
            // If the id is the same there is no issue.
            if(user.getId().equals(userId)) {
                return ok("user_nonexists");
            }
            // There already is an account with this email.
            return ok("user_exists");
        }

        return ok("user_nonexists");
    }

    public Result checkTraningCode(String trainingCode) {
        if(trainingRepo.getTraining(trainingCode) != null) {
            return ok("code_exists");
        } else {
            return ok("code_nonexists");
        }
    }

    public Result getTrainingForCategory(String category) {
        Category cat = catRepo.getCategoryByName(category);
        JsonNode node = Json.toJson(trainingRepo.getTrainingByCategory(cat.get_id()));

        return ok(node);
    }

    public Result getDatesForTraining(String trainingId) {
        User user = userRepo.getUser(ctx().session().get("email"));
        DateConverter converter = new DateConverter();
        List<ViewDate> dates;
        if(user != null) {
           dates = converter.getViewDatesWithoutTeacher(trainingId, user.getId(), true);
        } else {
            dates  = converter.getViewDatesWithoutTeacher(trainingId, "", true);
        }

        Collections.sort(dates);

        JsonNode node = Json.toJson(dates);
        return ok(node);
    }

    public Result addCategory(){
        Category newCategory;
        JsonNode node;
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String categoryStringValue = params.entrySet().iterator().next().getValue()[0].toLowerCase();
        newCategory = new Category(categoryStringValue);
        if(catRepo.addCategory(newCategory)){
            List<Category>categories = catRepo.getAllCategories();
            node = Json.toJson(categories);
            return ok(node);
        }
        String errorMessage = "Categorie Bestaat Al";
        node = Json.toJson(errorMessage);
        return ok(node);
    }

    public Result deleteCategory(){
        Category toDelete;
        JsonNode node;
        String message;
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        String categoryStringValue = params.entrySet().iterator().next().getValue()[0].toLowerCase();
        toDelete = catRepo.getCategoryByName(categoryStringValue);
        if(trainingRepo.findTrainingByCategoryId(toDelete.get_id())){
            message= "Categorie in gebruik";
            node = Json.toJson(message);
            return ok(node);
        }
        if(catRepo.removeCategory(toDelete)){
            List<Category>categories = catRepo.getAllCategories();
            node = Json.toJson(categories);
            return ok(node);
        }
        message = "Er is iets mis gegaan";
        node = Json.toJson(message);
        return ok(node);

    }
  
    public Result checkUserSignUp(String dateId, String userId) {
        boolean result = dateRepo.checkUserSignup(userId, dateId);
        if (result) {
            return ok("user_signedup");
        } else {
            return ok("no_signup");
        }
    }

    public Result checkTeacherOverlap() throws ParseException {
        List<String> dates = new ArrayList<>();
        int[] duration = new int[1];
        duration[0] = 0;
        List<String> teacherIds = new ArrayList<>();
        List<String> dateIds = new ArrayList<>();

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        parseParameters(params, dates, duration, teacherIds, dateIds);

        List<String> overlapErrorLines = new ArrayList<>();

        if(duration[0] != 0 && dates.size() == teacherIds.size()) {
            if(!performOverlapCheck(overlapErrorLines, dates, duration, teacherIds, dateIds)) {
                return ok("no_check");
            }
        } else {
            return ok("no_check");
        }

        if(overlapErrorLines.size() >= 1) {
            JsonNode node = Json.toJson(overlapErrorLines);
            return ok(node);
        }
        return ok("no_overlap");
    }

    private void parseParameters(Map<String, String[]> params, List<String> dates, int[] duration, List<String> teacherIds, List<String> dateIds ) {
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            switch(param.getKey()) {
                case "teacher[]":
                    teacherIds.addAll(Arrays.asList(param.getValue()));
                    break;
                case "date[]":
                    dates.addAll(Arrays.asList(param.getValue()));
                    break;
                case "duration":
                    duration[0] = parseString(param.getValue()[0]);
                    break;
                case "dateIds[]":
                    dateIds.addAll(Arrays.asList(param.getValue()));
                    break;
                default:
                    break;
            }
        }
    }

    private int parseString(String input) {
        if(input.indexOf('.') == -1) {
            return Integer.parseInt(input);
        } else {
            String wholeNumber = input.substring(0, input.indexOf('.'));
            return Integer.parseInt(wholeNumber);
        }
    }

    private boolean performOverlapCheck(List<String> overlapErrorLines, List<String> dates, int[] duration, List<String> teacherIds, List<String> dateIds) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        OverlapChecker checker = new OverlapChecker();
        if(dates.size() != dateIds.size()) {
            for (int i = dateIds.size() ; i < dates.size(); i++) {
                if(dates.get(i).equals("")) return false;
                if(teacherIds.get(i).equals("")) return false;

                Date other = df.parse(dates.get(i));
                String id = teacherIds.get(i);

                DateTime error = checker.checkOverlapForTeacher(other, duration[0], id);

                if (error != null) {
                    overlapErrorLines.add("overlap_detected_" + i);
                }
            }

            checkOverlapNewDates(overlapErrorLines,dates, duration[0], teacherIds);

        } else {
            for(int i = 0; i < dateIds.size(); i++) {
                updateDateTime(dateIds.get(i), df.parse(dates.get(i)));
                DateTime error = checker.checkOverlapForTeacher(dateRepo.getDateTime(dateIds.get(i)), teacherIds.get(i));
                if(error != null) {
                    overlapErrorLines.add("overlap_detected_" + i);
                }
            }
        }
        return true;
    }

    private void updateDateTime(String dateId, Date newDate) {
        DateTime dt = dateRepo.getDateTime(dateId);
        dt.setDate(newDate);
        dateRepo.updateDateTime(dt);
    }

    private void checkOverlapNewDates(List<String> overlapErrorLines, List<String> dates, int duration, List<String> teacherIds) throws ParseException {
        List<DateTime> dateTimes = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

        for(int i = 0; i < dates.size(); i++) {
            DateTime dt = new DateTime(df.parse(dates.get(i)), "", teacherIds.get(i), duration);
            dateTimes.add(dt);
        }

        for(int j = 0; j < dateTimes.size(); j++) {
            for(int k = 0; k < dateTimes.size(); k++) {
                if( j != k) {
                    DateTime error = dateTimes.get(j).checkOverlap(dateTimes.get(k));
                    if(error != null) {
                        overlapErrorLines.add("overlap_detected_" + j);
                    }
                }
            }
        }
    }

    // Example stuff below.
    public Result myPostAction() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            System.out.println(param.getKey() + " = " + param.getValue()[0]);
            examples.add(param.getValue()[0]);
            // Do other stuff here...
        }

        // Return the new complete list.
        // Of course you could fetch data from a database or any other source.
        JsonNode node = Json.toJson(examples);
        return ok(node);
    }

    public Result myExampleView() {
        return ok(example.render());
    }
}
