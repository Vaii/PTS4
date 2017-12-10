package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dal.contexts.DateTimeMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
import dal.repositories.UserRepository;
import models.storage.DateTime;
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

    List<String> examples = new ArrayList<>();

    public Result checkEmail(String email) {
        if(userRepo.getUser(email) != null) {
            return ok("user_exists");
        } else {
            return ok("user_nonexists");
        }
    }

    public Result checkTraningCode(String trainingCode) {
        if(trainingRepo.getTraining(trainingCode) != null) {
            return ok("code_exists");
        } else {
            return ok("code_nonexists");
        }
    }

    public Result getTrainingForCategory(String category) {
        JsonNode node = Json.toJson(trainingRepo.getTrainingByCategory(category));

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

        JsonNode node = Json.toJson(dates);
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
        int duration = 0;
        List<String> teacherIds = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            switch(param.getKey()) {
                case "teacher[]":
                    teacherIds.addAll(Arrays.asList(param.getValue()));
                    break;
                case "date[]":
                    dates.addAll(Arrays.asList(param.getValue()));
                    break;
                case "duration":
                    duration = Integer.parseInt(param.getValue()[0]);
                    break;
                default:
                    break;
            }
        }

        List<String> overlapErrorLines = new ArrayList<>();

        if(duration != 0 && dates.size() == teacherIds.size()) {
            for (int i = 0; i < dates.size(); i++) {
                if(dates.get(i).equals("")) return ok("no_check");
                if(teacherIds.get(i).equals("")) return ok("no_check");

                Date other = df.parse(dates.get(i));
                String id = teacherIds.get(i);

                OverlapChecker checker = new OverlapChecker();
                DateTime error = checker.checkOverlapForTeacher(other, duration, id);

                if (error != null) {
                    overlapErrorLines.add("overlap_detected_" + i);
                }
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
