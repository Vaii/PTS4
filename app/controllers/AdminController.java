package controllers;

import dal.contexts.CategoryContext;
import dal.contexts.DateTimeMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.CategoryRepository;
import dal.repositories.DateTimeRepository;
import dal.repositories.UserRepository;
import models.storage.*;
import models.util.DateConverter;
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
import views.html.adminpanel.teacherDeleteError;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController extends Controller{

    private UserRepository uRepo;
    private DateTimeRepository dRepo;
    private CategoryRepository cRepo;
    private Form<User> form;
    private Form<User> userForm;
    private Form<User> filledForm;

    @Inject
    public AdminController(FormFactory formFactory){
        this.uRepo = new UserRepository(new UserMongoContext("User"));
        this.dRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
        this.cRepo = new CategoryRepository(new CategoryContext("Category"));
        this.form = formFactory.form(User.class);
        this.userForm = formFactory.form(User.class);
        this.filledForm = formFactory.form(User.class);

    }

    public Result removeUser(String id){
        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MEDEWERKERKENNISCENTRUM)){
            User userToRemove = uRepo.getUserByID(id);
            if(userToRemove.getRole().equals(Role.DOCENT)) {
                List<DateTime> dates = dRepo.getDateTimeForTeacher(userToRemove.getId());
                if(!dates.isEmpty()) {
                    // Teacher still has dates assigned to him, cancel the delete operation.
                    DateConverter converter = new DateConverter();
                    converter.convert(dates, Secured.getUserInfo(ctx()).getId());
                    return ok(teacherDeleteError.render("Error", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())
                            ,converter.convert(dates, Secured.getUserInfo(ctx()).getId())));
                } else {
                    uRepo.removeUser(userToRemove); // Remove user from user table.
                    dRepo.removeUser(id); // Remove user from possible trainee fields.
                    return ok(message.render("Admin", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())
                            ,"Account succesvol verwijderd", "/admin"  ));
                }
            } else {
                uRepo.removeUser(userToRemove); // Remove user from user table.
                dRepo.removeUser(id); // Remove user from possible trainee fields.
                return ok(message.render("Admin", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())
                        ,"Account succesvol verwijderd", "/admin"  ));
            }
        }
        return notFound();
    }

    @Security.Authenticated(Secured.class)
    public Result overview(){
        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MEDEWERKERKENNISCENTRUM)){
            return ok(overview.render("overview",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }
        else{
            return notFound();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manageAccount(String email){
        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MEDEWERKERKENNISCENTRUM)){
            userForm = form.fill(uRepo.getUser(email));

            List<User> managers = uRepo.getAllManagers();
            Map<String, String> managerMap = mapManager(managers);
            List<Category> categories = cRepo.getAllCategories();

            User user = uRepo.getUser(email);

            return ok(manageaccount.render("Manage Account",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), userForm, managerMap, categories, user));
        }
        else{
            return notFound();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result editUser(){

        filledForm = form.bindFromRequest();

        List<User> managers = uRepo.getAllManagers();
        Map<String, String> managerMap = mapManager(managers);
        List<Category> categories = cRepo.getAllCategories();
        String email = filledForm.field("email").value();

        if(filledForm.hasErrors()){
            return (badRequest(manageaccount.render("Manage account",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), userForm, managerMap, categories, uRepo.getUser(email))));
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

        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MEDEWERKERKENNISCENTRUM)){
            return ok(accountSelector.render("Select Account",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), allUsers));
        }
        else{
            return notFound();
        }

    }

    @Security.Authenticated(Secured.class)
    public Result creationPage(){
        if(Secured.getUserInfo(ctx()).getRole().equals(Role.MEDEWERKERKENNISCENTRUM)){
            List<User> manager = uRepo.getAllManagers();

            Map<String, String> managerInfo = mapManager(manager);
            return ok(accountcreation.render("Account Creation",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, managerInfo));
        }
        else{
            return notFound();
        }
    }

    private Map<String, String> mapManager(List<User> managers){

        Map<String, String> managerMap = new HashMap<>();
        for(User m : managers){
            managerMap.put(m.getId(), m.getEmail());
        }

        return managerMap;
    }

    @Security.Authenticated(Secured.class)
    public Result createUser(){
        Form<User> newFilledForm = form.bindFromRequest();
        User newUser = newFilledForm.get();
        String password = newFilledForm.field("password").value();
        String toValidate = newFilledForm.field("validation").value();

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
