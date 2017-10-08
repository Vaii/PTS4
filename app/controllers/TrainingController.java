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
import views.html.training.addtraining;
import views.html.training.submit;
import views.html.training.trainingoverview;
import views.html.training.managetraining;
import views.html.training.removetraining;
import views.html.training.*;
import views.html.training.edit;
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

    public Result manage(){
        return ok(managetraining.render(trainingRepository.getAll(), null, "Trainingen", Secured.isLoggedIn(ctx()),Secured.getUserInfo(ctx()),form));
    }

    public Result manageTraining(String id){
        if (id == null){
            return ok(managetraining.render(trainingRepository.getAll(),null,
                    "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),form));
        }
        else {
            Form<Training> editForm = form.fill(trainingRepository.getTraining(id));
            return ok(managetraining.render(trainingRepository.getAll(),trainingRepository.getTraining(id),
                    "Trainingen" , Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),editForm));
        }
    }

    public Result removeTraining(String id){
        if (id == null){
            return ok(managetraining.render(trainingRepository.getAll(),null,"Trainingen", Secured.isLoggedIn(ctx()),Secured.getUserInfo(ctx()),form));
        }
        else {
            Training t = trainingRepository.getTraining(id);
            trainingRepository.removeTraining(t);
            return ok(removetraining.render(t,"Trainingen", Secured.isLoggedIn(ctx()),Secured.getUserInfo(ctx())));
        }

    }

    public Result edit(String id) {
        Form<Training> editFrom = form.fill(trainingRepository.getTraining(id));
        if(editFrom.hasErrors()){
            flash("danger", "Wrong values");
            return badRequest(managetraining.render(trainingRepository.getAll(),null,
                    "Trainingen" , Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),form));
        }
        else {
            Form<Training> filledForm = form.bindFromRequest();
            Training training = filledForm.get();
            training.set_id(id);
            trainingRepository.updateTraining(training);
            return ok(edit.render(training,"Trainingen",Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }
    }
}
