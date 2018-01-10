package controllers;

import dal.contexts.*;
import dal.interfaces.DateTimeContext;
import dal.repositories.*;
import models.storage.*;
import models.util.DateConverter;
import models.util.FormConverter;
import models.util.OverlapChecker;
import models.view.ViewDate;
import models.view.ViewTraining;
import models.view.ViewTuition;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.training.signupError;

import javax.inject.Inject;
import javax.swing.text.html.FormView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerController extends Controller {

    private UserRepository uRepo = new UserRepository(new UserMongoContext("User"));
    private DateTimeRepository dtRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    private TrainingRepository tRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private CategoryRepository cRepo = new CategoryRepository(new CategoryMongoContext("Category"));
    private TuitionFormRepository tutRepo = new TuitionFormRepository(new TuitionFormMongoContext("TuitionForm"));

    private FormFactory formFactory;
    private DynamicForm form2;

    private Form<TuitionForm> emptyForm;
    private Form<TuitionForm> filledForm;

    @Inject
    public ManagerController(FormFactory formFactory){

        this.formFactory = formFactory;
        this.form2 = formFactory.form();
        this.filledForm = formFactory.form(TuitionForm.class);
        this.emptyForm = formFactory.form(TuitionForm.class);
    }

    @Security.Authenticated(Secured.class)
    public Result employeeOverview(){

        if(Secured.getUserInfo(ctx()).getRole().equals(Role.BUSINESSUNITMANAGER)){

            List<User> employee = uRepo.getUserByManager(Secured.getUserInfo(ctx()).getId());
            Map<User, List<ViewTraining>> userAndTraining = new HashMap<>();

            DateConverter converter = new DateConverter();

            for (User e : employee){
                List<DateTime> signUps = dtRepo.getDateTimeForUser(e.getId());
                List<ViewTraining> viewTrainings = new ArrayList<>();

                for(DateTime dt : signUps) {
                    Training t = tRepo.getTrainingById(dt.getTrainingID());
                    Category category = cRepo.getCategoryById(t.getCategoryid());
                    viewTrainings.add(new ViewTraining(t, converter.convert(dt), category));
                }

                userAndTraining.put(e, viewTrainings);
            }

            return ok(views.html.manager.employeeOverview.render("Employee Overview",
                    Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), userAndTraining));
        }

        return notFound();
    }

    @Security.Authenticated(Secured.class)
    public Result formOverview(){

        List<TuitionForm> forms = tutRepo.getForm(Secured.getUserInfo(ctx()).getId());
        FormConverter fConverter = new FormConverter();
        List<ViewTuition> viewTuitions = new ArrayList<>();

        for(TuitionForm f : forms){
            viewTuitions.add(fConverter.convertForm(f));
        }

        return ok(views.html.manager.formOverview.render("Form overview", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), viewTuitions));
    }

    @Security.Authenticated(Secured.class)
    public Result inspectForm(String id){

        TuitionForm tutForm = tutRepo.getSpecific(id);
        FormConverter formConverter = new FormConverter();



        List<User> allManagers = uRepo.getAllManagers();
        Map<String, String> managerMap = mapManager(allManagers);

        return ok(views.html.manager.approveForm.render("Approve form", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formConverter.convertForm(tutForm), emptyForm , managerMap));
    }

    @Security.Authenticated(Secured.class)
    public Result approveForm(){

        filledForm = filledForm.bindFromRequest();
        TuitionForm handledForm = filledForm.get();

        if(handledForm.getStatus() == Status.GOEDGEKEURD){
            tutRepo.updateForm(handledForm);

            DateTime signUpDate = dtRepo.getDateTime(handledForm.getDateTimeID());

            signUpDate.addTrainee(handledForm.getEmployeeID());
            dtRepo.updateDateTime(signUpDate);

        } else if(handledForm.getStatus() == Status.AFGEWEZEN){
            tutRepo.updateForm(handledForm);
        }

        return redirect(routes.ManagerController.formOverview());
    }

    private Map<String, String> mapManager(List<User> managers){

        Map<String, String> managerMap = new HashMap<>();
        for(User m : managers){
            managerMap.put(m.getId(), m.getEmail());
        }

        return managerMap;
    }

    private DateTime detectedOverlap(DateTime signUpDate, OverlapType type){
        OverlapChecker checker = new OverlapChecker();
        DateTime overlapError;
        if (type == OverlapType.STUDENT){
            overlapError = checker.checkOverlapForTrainee(signUpDate, Secured.getUserInfo(ctx()).getId());
        }
        else{
            overlapError = checker.checkOverlapForTeacher(signUpDate, signUpDate.getTeacherID());
        }
        return overlapError;
    }
}
