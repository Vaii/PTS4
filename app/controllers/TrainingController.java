package controllers;

<<<<<<< HEAD
import models.Secured;
=======
import dal.contexts.TrainingMongoContext;
import dal.repositories.TrainingRepository;
import models.Training;
import play.data.Form;
import play.data.FormFactory;
>>>>>>> origin/Training-management-V3
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addtraining;
import views.html.submit;
import views.html.trainingoverview;

<<<<<<< HEAD
=======
import javax.inject.Inject;

>>>>>>> origin/Training-management-V3
/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller{

<<<<<<< HEAD
    public Result signUpCourse(){
        return ok(signUpCourse.render("Training Inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }
=======
    TrainingRepository trainingRepository = new TrainingRepository(new TrainingMongoContext("Training"));
    private Form<Training> form;

    @Inject
    public TrainingController(FormFactory formFactory) {
        this.form = formFactory.form(Training.class);
    }

    public Result addtraining() {
        return ok(addtraining.render(form));
    }

    public Result submit() { // submit new training
        Form<Training> filledForm = form.bindFromRequest();
        Training newTraining = filledForm.get();

        trainingRepository.addTraining(newTraining);
        Training t = trainingRepository.getTraining(newTraining.getTrainingCode());
        return ok(submit.render(t));
    }


    public Result overview(){
        return ok(trainingoverview.render(trainingRepository.getAll(),null));
    }

    public Result trainingOverview(String id){
        if (id == null){
            return ok(trainingoverview.render(trainingRepository.getAll(),null));
       }
        else {
            return ok(trainingoverview.render(trainingRepository.getAll(),trainingRepository.getTraining(id)));
        }

    }
>>>>>>> origin/Training-management-V3
}
