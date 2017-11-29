package controllers;

import dal.contexts.DateTimeMongoContext;
import dal.contexts.UserMongoContext;
import dal.interfaces.DateTimeContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.UserRepository;
import models.storage.DateTime;
import models.storage.Role;
import models.storage.Secured;
import models.storage.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerController extends Controller {

    private UserRepository uRepo;
    private DateTimeRepository dtRepo;


    public ManagerController(){
        this.uRepo = new UserRepository(new UserMongoContext("User"));
        this.dtRepo = new DateTimeRepository(new DateTimeMongoContext("Datetime"));
    }

    @Security.Authenticated(Secured.class)
    public Result employeeOverview(){

        if(Secured.getUserInfo(ctx()).getRole().equals(Role.BUSINESSUNITMANAGER)){

            List<User> employee = uRepo.getUserByManager(Secured.getUserInfo(ctx()).getId());
            Map<User, List<DateTime>> userAndTraining = new HashMap<>();

            for (User e : employee){
                List<DateTime> signUps = dtRepo.getDateTimeForUser(e.getId());

                userAndTraining.put(e, signUps);
            }

            return ok(views.html.manager.employeeOverview.render("Employee Overview",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), userAndTraining));
        }

        return notFound();
    }
}
