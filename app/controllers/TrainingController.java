package controllers;


import dal.contexts.TrainingMongoContext;
import dal.repositories.TrainingRepository;
import models.Secured;
import models.Training;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.training.*;
import views.html.signUpCourse;

import javax.inject.Inject;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller{

    @Security.Authenticated(Secured.class)
    public Result signUpCourse(){
        return ok(signUpCourse.render("Training Inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    TrainingRepository trainingRepository = new TrainingRepository(new TrainingMongoContext("Training"));
    private Form<Training> form;

    @Inject
    public TrainingController(FormFactory formFactory) {
        this.form = formFactory.form(Training.class);
    }

    public Result addtraining() {
        return ok(addtraining.render(form,Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Add Training"));
    }

    public Result submit() { // submit new training
        Form<Training> filledForm = form.bindFromRequest();

        if(filledForm.hasErrors()) {
            flash("danger", "Please fill valid in");
            return badRequest(addtraining.render(filledForm, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Add Training"));
        }
        else {
            Training newTraining = filledForm.get();
            trainingRepository.addTraining(newTraining);
            Training t = trainingRepository.getTraining(newTraining.getTrainingCode());
            return ok(submit.render(t, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }
    }


    public Result overview(){
        return ok(trainingoverview.render(trainingRepository.getAll(),null,
                "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    public Result trainingOverview(String id){
        if (id == null){
            return ok(trainingoverview.render(trainingRepository.getAll(),null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
       }
        else {
            return ok(trainingoverview.render(trainingRepository.getAll(),trainingRepository.getTraining(id),
                    "Trainingen" , Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }

    }
}
