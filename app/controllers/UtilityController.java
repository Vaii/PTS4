package controllers;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.JsonNode;
import dal.contexts.DateTimeMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
=======
import dal.contexts.UserMongoContext;
>>>>>>> parent of 204d247... Added JQuery Checks
import dal.repositories.UserRepository;
import models.util.DateConverter;
import models.view.ViewDate;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class UtilityController extends Controller {

    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
<<<<<<< HEAD
    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
=======
>>>>>>> parent of 204d247... Added JQuery Checks

    public Result checkEmail(String email) {
        if(userRepo.getUser(email) != null) {
            return ok("user_exists");
        } else {
            return ok("user_nonexists");
        }
    }
<<<<<<< HEAD

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
        DateConverter converter = new DateConverter();
        List<ViewDate> dates = converter.getViewDates(trainingId);

        JsonNode node = Json.toJson(dates);
        return ok(node);
    }
=======
>>>>>>> parent of 204d247... Added JQuery Checks
}
