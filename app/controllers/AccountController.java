package controllers;

import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import models.Secured;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.*;
import play.cache.*;

import javax.inject.Inject;

public class AccountController extends Controller {


    private UserRepository userRepo;
    private FormFactory formFactory;
    private DynamicForm requestData;

    @Inject
    public AccountController(FormFactory formFactory){

        this.formFactory = formFactory;
        this.userRepo = new UserRepository(new UserMongoContext("Users"));
    }

    public Result login(){
        return ok(login.render("Login", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));

    }

    public Result authentication(){

        requestData = formFactory.form().bindFromRequest();
        String username = requestData.get("username");
        String password = requestData.get("password");

        if(requestData.hasErrors()){
            play.Logger.ALogger logger = play.Logger.of(getClass());
            logger.error("Errors = {}", requestData.errors());
            return redirect(routes.AccountController.login());
        }
        else{

            if(userRepo.login(username, password)){
                session().clear();
                session("email", username);
                return redirect(routes.Application.index());
            }
            return redirect(routes.AccountController.login());
        }
    }

    @Security.Authenticated(Secured.class)
    public Result profile(){
        return ok(profile.render("Profile", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    @Security.Authenticated(Secured.class)
    public Result logout(){
        session().clear();
        return redirect(routes.Application.index());
    }

}
