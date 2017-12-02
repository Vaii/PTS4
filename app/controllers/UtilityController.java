package controllers;

import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.TrainingRepository;
import dal.repositories.UserRepository;
import play.mvc.Controller;
import play.mvc.Result;

public class UtilityController extends Controller {

    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));

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
}
