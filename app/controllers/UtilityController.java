package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dal.contexts.DateTimeMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
import dal.repositories.UserRepository;
import models.storage.Secured;
import models.storage.User;
import models.util.DateConverter;
import models.view.ViewDate;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

public class UtilityController extends Controller {

    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));

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
           dates = converter.getViewDates(trainingId, user.getId());
        } else {
            dates  = converter.getViewDates(trainingId, "");
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
}
