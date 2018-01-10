package controllers;

import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import models.storage.Secured;
import models.storage.User;
import models.util.Redirect;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.account.*;
import views.html.shared.message;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountController extends Controller {


    private UserRepository userRepo;
    private FormFactory formFactory;
    private Form<User> form;
    private DynamicForm form2;

    private static final String LOGIN = "Login";
    private static final String REGISTER = "Register";

    @Inject
    public AccountController(FormFactory formFactory){

        this.formFactory = formFactory;
        this.userRepo = new UserRepository(new UserMongoContext("User"));
        this.form = formFactory.form(User.class);
        this.form2 = formFactory.form();
    }

    public Result login(){
        return ok(redirectlogin.render(LOGIN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form2, false));

    }

    public Result redirectlogin(){
        return ok(redirectlogin.render(LOGIN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form2, false));
    }

    public Result authentication(){

        form2 = formFactory.form().bindFromRequest();
        String username = form2.get("email");
        String password  = form2.get("password");

        if(form2.hasErrors()){
            play.Logger.ALogger logger = play.Logger.of(getClass());
            logger.error("Errors = {}", form2.errors());
            return redirect(routes.AccountController.login());
        }
        else{
            return login(username, password, ctx().session().get("previousUrl"));
        }
    }

    public Result register(){
        return ok(register.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
    }

    public Result registerAccount(){
        Form<User> filledForm = form.bindFromRequest();
        User user = filledForm.get();
        String password = filledForm.field("password").value();
        String validation = filledForm.field("validation").value();
        String company = filledForm.field("Company").value();
        String email = filledForm.field("email").value();

        if(!password.equals(validation)) {
            return badRequest(register.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), filledForm.withError("validation", "Wachtwoorden komen niet overeen")));
        }

        String companycheck = company.replaceAll("\\s+", "").toLowerCase();
        if(companycheck.equals("infosupport")){
            return badRequest( register.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), filledForm.withError("Company", "Info Support medewerkers krijgen een account, neem contact op met een administrator")));
        }
        
        String emailcheck = email.toLowerCase();
        if( emailcheck.contains("infosupport")){
            return badRequest( register.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), filledForm.withError("email", "Info Support medewerkers krijgen een account, neem contact op met een administrator")));
        }

        if(filledForm.hasErrors()){
            return badRequest(register.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
        }

        if(userRepo.addUser(user, password)){
            return login(user.getEmail(), password, ctx().session().get("previousUrl"));
        }

        return badRequest(register.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
    }

    @Security.Authenticated(Secured.class)
    public Result profile(){
        return ok(profile.render("Profile", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    @Security.Authenticated(Secured.class)
    public Result logout(){
        session().clear();
        return redirect(routes.ApplicationController.index());
    }

    private Result login(String email, String password, String previousUrl) {
        if(userRepo.login(email, password)){
            session().clear();
            if(previousUrl != null) {
                session("previousUrl", previousUrl);
            } else {
                session("previousUrl", "/");
            }
            session("email", email);

            return redirect(session().get("previousUrl"));
        }
        return badRequest(login.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form2, true));
    }

    @Security.Authenticated(Secured.class)
    public Result editProfile() {
        Form<User> filledForm = form.fill(Secured.getUserInfo(ctx()));
        return ok(editprofile.render("Profile", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),filledForm));
    }

    @Security.Authenticated(Secured.class)
    public Result edit() {
        form = form.bindFromRequest();

        List<User> managers = userRepo.getAllManagers();
        Map<String, String> managerMap = mapManager(managers);
        //List<Category> categories = cRepo.getAllCategories();
        String email = form.field("email").value();

       // List<String> skills = getSkills(filledForm);

        if(form.hasErrors()){
            return (badRequest(editprofile.render("Edit account",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form)));
        }
        User user = form.get();
        //user.setSkillIds(skills);

        if(userRepo.updateUser(user)){
            return ok(message.render("Edit Succes",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),
                    "Het account is gewijzigd", "/account/profile"));
        }

        return notFound();
    }

    private Map<String, String> mapManager(List<User> managers){
        Map<String, String> managerMap = new HashMap<>();
        for(User m : managers){
            managerMap.put(m.getId(), m.getEmail());
        }
        return managerMap;
    }
}
