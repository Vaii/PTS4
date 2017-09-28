package controllers;

import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import play.mvc.*;
import views.html.*;
import play.cache.*;

public class AccountController extends Controller {

    private UserRepository userRepo = new UserRepository(new UserRepository(new UserMongoContext("Users")));

    public Result login(){
        return ok(login.render());
    }

    public Result authentication(String email, String password){

        if(userRepo.login(email, password)){

            String uuid = java.util.UUID.randomUUID().toString();

    }
        return ok(login.render());
    }
}
