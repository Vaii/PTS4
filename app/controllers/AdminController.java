package controllers;

import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import models.Secured;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.adminpanel.overview;
import views.html.adminpanel.accountcreation;

import javax.inject.Inject;

public class AdminController extends Controller{

    private UserRepository uRepo;
    private FormFactory formFactory;
    private Form<User> form;

    @Inject
    public AdminController(FormFactory formFactory){
        this.formFactory = formFactory;
        this.uRepo = new UserRepository(new UserMongoContext("User"));
        this.form = formFactory.form(User.class);
    }

    public Result overview(){
        return ok(overview.render("overview",
                Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    public Result creationPage(){
        return ok(accountcreation.render("Account Creation",
                Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
    }

    public Result createUser(){
        return null;
    }
}
