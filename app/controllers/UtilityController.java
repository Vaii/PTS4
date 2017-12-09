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
        Date other = null;
        int duration = 0;
        String id = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            System.out.println(param.getKey() + " = " + param.getValue()[0]);
            switch(param.getKey()) {
                case "teacher":
                    id = param.getValue()[0];
                    break;
                case "date":
                    other = df.parse(param.getValue()[0]);
                    break;
                case "duration":
                    duration = Integer.parseInt(param.getValue()[0]);
                    break;
                default:
                    break;
            }
        }

        if(!id.equals("") && duration != 0 && other != null) {
            System.out.println("Performing check");
            OverlapChecker checker = new OverlapChecker();
            DateTime error = checker.checkOverlapForTeacher(other, duration, id);

            System.out.println("Done performing overlap check");
            if(error != null) {
                return ok("overlap_detected");
            } else {
                return ok("no_overlap");
            }
        } else {
            return ok("no_check");
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
