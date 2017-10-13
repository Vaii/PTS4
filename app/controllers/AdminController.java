package controllers;

import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import models.Role;
import models.Secured;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.adminpanel.overview;
import views.html.adminpanel.accountcreation;
import views.html.shared.message;

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

    @Security.Authenticated(Secured.class)
    public Result overview(){
        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MedewerkerKenniscentrum)){
            return ok(overview.render("overview",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }
        else{
            return null;
        }
    }

    @Security.Authenticated(Secured.class)
    public Result creationPage(){
        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MedewerkerKenniscentrum)){
            return ok(accountcreation.render("Account Creation",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
        }
        else{
            return null;
        }
    }

    @Security.Authenticated(Secured.class)
    public Result createUser(){
        Form<User> filledForm = form.bindFromRequest();
        User newUser = filledForm.get();
        String password = filledForm.field("password").value();
        String toValidate = filledForm.field("validation").value();

        if(password.equals(toValidate)){
            if(uRepo.addUser(newUser, password)){
                return ok(message.render("Admin", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())
                        ,"Het account voor " + newUser.getEmail() + " is succesvol aangemaakt", "/admin"  ));
            }
        }
        else{
            return ok(overview.render("overview", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }

        return null;
    }
}
