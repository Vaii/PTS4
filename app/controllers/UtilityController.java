package controllers;

import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import play.mvc.Controller;
import play.mvc.Result;

public class UtilityController extends Controller {

    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));

    public Result checkEmail(String email) {
        if(userRepo.getUser(email) != null) {
            return ok("user_exists");
        } else {
            return ok("user_nonexists");
        }
    }
}
