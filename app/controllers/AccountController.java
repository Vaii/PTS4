package controllers;

import dal.contexts.UserMongoContext;
import dal.repositories.UserRepository;
import models.storage.Secured;
import models.storage.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.account.*;

import javax.inject.Inject;

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
        flash("url", request().getHeader("referer"));
        return ok(login.render(LOGIN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form2, false));

    }

    public Result redirectlogin(){
        flash("url", request().getHeader("referer"));
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
            if(login(username, password)) {
                return redirect(routes.ApplicationController.index());
            }
            return ok(login.render(LOGIN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form2, true));
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

        if(!password.equals(validation)) {
            return badRequest(register.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), filledForm.withError("validation", "Wachtwoorden komen niet overeen")));
        }

        String companycheck = company.replaceAll("\\s+", "").toLowerCase();
        if(companycheck.equals("infosupport")){
            return badRequest( register.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), filledForm.withError("Company", "Info Support medewerkers krijgen een account, neem contact op met een administrator")));
        }

        if(filledForm.hasErrors()){
            return badRequest(register.render(REGISTER, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form));
        }

        if(userRepo.addUser(user, password)){
            if(login(user.getEmail(), password)) {
                return redirect(routes.ApplicationController.index());
            }
            return ok(registerSuccess.render(LOGIN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
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

    private boolean login(String email, String password) {
        if(userRepo.login(email, password)){
            session().clear();
            session("email", email);

            return true;
        }
        return false;
    }

}
