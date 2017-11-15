package controllers;

import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import models.storage.Role;
import models.storage.Secured;
import models.storage.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.adminpanel.overview;
import views.html.adminpanel.accountcreation;
import views.html.shared.message;
import views.html.adminpanel.manageaccount;
import views.html.adminpanel.accountSelector;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController extends Controller{

    private UserRepository uRepo;
    private FormFactory formFactory;
    private Form<User> form;
    private Form<User> userForm;
    private Form<User> filledForm;

    @Inject
    public AdminController(FormFactory formFactory){
        this.formFactory = formFactory;
        this.uRepo = new UserRepository(new UserMongoContext("User"));
        this.form = formFactory.form(User.class);
        this.userForm = formFactory.form(User.class);
        this.filledForm = formFactory.form(User.class);

    }

    @Security.Authenticated(Secured.class)
    public Result overview(){
        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MedewerkerKenniscentrum)){
            return ok(overview.render("overview",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }
        else{
            return notFound();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manageAccount(String email){
        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MedewerkerKenniscentrum)){
            userForm = form.fill(uRepo.getUser(email));
            return ok(manageaccount.render("Manage Account",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), userForm));
        }
        else{
            return notFound();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result editUser(){

        filledForm = form.bindFromRequest();

        if(filledForm.hasErrors()){
            return (badRequest(manageaccount.render("Manage account",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), userForm)));
        }
        User user = filledForm.get();

        if(uRepo.updateUser(user)){
            return ok(message.render("Edit Succes",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),
                    "Het account is gewijzigd", "/admin"));
        }

        return notFound();
    }

    @Security.Authenticated(Secured.class)
    public Result accountSelector(){
        List<User> allUsers = uRepo.getAll();

        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MedewerkerKenniscentrum)){
            return ok(accountSelector.render("Select Account",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), allUsers));
        }
        else{
            return notFound();
        }

    }

    @Security.Authenticated(Secured.class)
    public Result creationPage(){
        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MedewerkerKenniscentrum)){
            List<User> manager = uRepo.getAllManagers();

            Map<String, String> managerInfo = new HashMap<>();

            for(User m : manager){
                managerInfo.put(m.get_id(), m.getEmail());
            }
            return ok(accountcreation.render("Account Creation",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, managerInfo));
        }
        else{
            return notFound();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result createUser(){
        Form<User> filledForm = form.bindFromRequest();
        User newUser = filledForm.get();
        String password = filledForm.field("password").value();
        String toValidate = filledForm.field("validation").value();

        if(filledForm.field("ManagerCreation").value() != null){
            newUser.setManager(filledForm.field("ManagerCreation").value());
        }

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
