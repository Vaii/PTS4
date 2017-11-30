package controllers;

import dal.contexts.DateTimeMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.interfaces.DateTimeContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
import dal.repositories.UserRepository;
import models.storage.*;
import models.view.ViewDate;
import models.view.ViewTraining;
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
    private TrainingRepository tRepo;


    public ManagerController(){
        this.uRepo = new UserRepository(new UserMongoContext("User"));
        this.dtRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
        this.tRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    }

    @Security.Authenticated(Secured.class)
    public Result employeeOverview(){

        if(Secured.getUserInfo(ctx()).getRole().equals(Role.BUSINESSUNITMANAGER)){

            List<User> employee = uRepo.getUserByManager(Secured.getUserInfo(ctx()).getId());
            Map<User, List<ViewTraining>> userAndTraining = new HashMap<>();

            for (User e : employee){
                List<DateTime> signUps = dtRepo.getDateTimeForUser(e.getId());
                List<ViewTraining> viewTrainings = new ArrayList<>();

                for(DateTime d : signUps){
                    Training t = tRepo.getTrainingById(d.getTrainingID());
                    viewTrainings.add(new ViewTraining(t, d));
                }

                userAndTraining.put(e, viewTrainings);
            }

            return ok(views.html.manager.employeeOverview.render("Employee Overview",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), userAndTraining));
        }

        return notFound();
    }
}
