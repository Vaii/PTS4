package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import dal.contexts.*;
import dal.repositories.*;
import models.storage.*;
import models.util.OverlapChecker;
import models.view.ViewDate;
import models.view.ViewTraining;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.shared.message;
import views.html.training.*;
import views.html.teacher.*;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller {

    private static final String LOCATION = "Location";
    private static final String ADDTRAINING = "Add training";
    private static final String TEACHER = "Teacher";
    private static final String TRAININGCODE = "trainingCode";
    private static final String TRAININGEN = "Trainingen";
    private static final String DATEFORMAT = "yyyy-MM-dd";

    private Form<TuitionForm> tuitionFormForm;
    private TuitionFormRepository tutRepo = new TuitionFormRepository(new TuitionFormMongoContext("TuitionForm"));
    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private LocationRepository locationRepo = new LocationRepository(new LocationMongoContext("Location"));
    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    private SharedRepository sharedRepo = new SharedRepository(new SharedMongoContext());

    private FormFactory formFactory;
    private Form<Training> form;

    @Inject
    public TrainingController(FormFactory formFactory) {
        this.formFactory = formFactory;
        this.form = formFactory.form(Training.class);
        this.tuitionFormForm = formFactory.form(TuitionForm.class);
    }

    @Security.Authenticated(Secured.class)
    public Result signUpCourse(String id, String trainingID) {

        if (id == null) {
            return notFound();
        } else {
            if (Secured.getUserInfo(ctx()).getRole() != Role.EXTERN) {
                return ok(signUpCourseEmployee.render("Training inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dateRepo.getDateTime(id) , trainingRepo.getTraining(trainingID), tuitionFormForm));
            } else {
                DateTime signUpDate = dateRepo.getDateTime(id);
                DateTime overlapError = detectedOverlap(signUpDate, OverlapType.STUDENT);

                if(overlapError != null) {
                    return ok(signupError.render("Error", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),trainingRepo.getTrainingById(signUpDate.getTrainingID())  ,trainingRepo.getTrainingById(overlapError.getTrainingID()),signUpDate, overlapError ,"/overview") );
                }

                signUpDate.addTrainee(Secured.getUserInfo(ctx()).getId());
                dateRepo.updateDateTime(signUpDate);
                return ok(message.render("Succesvol Ingeschreven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),
                        "U bent succesvol ingeschreven voor de de training", "/"));
            }
        }
    }

    public Result signUpEmployee(String id, String trainingID) {

        Form<TuitionForm> filledTuitionForm = tuitionFormForm.bindFromRequest();

        if (filledTuitionForm.hasErrors()) {
            return badRequest(signUpCourseEmployee.render("Training inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dateRepo.getDateTime(id), trainingRepo.getTraining(trainingID), tuitionFormForm));
        } else {
            TuitionForm filledForm = filledTuitionForm.get();
            filledForm.setTotalCosts(filledForm.getStudyCosts() + filledForm.getAccommodationCosts() + filledForm.getExaminationFees() + filledForm.getTransportCosts() + filledForm.getExtraCosts());
            tutRepo.addForm(filledForm);

            DateTime signUpDate = dateRepo.getDateTime(id);
            DateTime overlapError = detectedOverlap(signUpDate, OverlapType.STUDENT);
            if(overlapError != null) {
                return ok(signupError.render("Error", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),trainingRepo.getTrainingById(signUpDate.getTrainingID())  ,trainingRepo.getTrainingById(overlapError.getTrainingID()),signUpDate, overlapError ,"/overview") );
            }

            signUpDate.addTrainee(Secured.getUserInfo(ctx()).getId());
            dateRepo.updateDateTime(signUpDate);
            return ok(message.render("Inschrijving ingediend", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),
                    "De aanvraag voor de training is succesvol ingedient", "/"));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result addTraining() {
        List<Location> locations = locationRepo.getAll();
        List<User> teachers = userRepo.getAllTeachers();

        JsonNode locationJson = Json.toJson(locations);
        JsonNode teacherJson = Json.toJson(teachers);

        return ok(addtraining.render(form, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), ADDTRAINING, locations, teachers, locationJson, teacherJson));
    }

    @Security.Authenticated(Secured.class)
    public Result submit() throws ParseException { // submit new training
        DynamicForm trainingData = formFactory.form().bindFromRequest();

        List<Location> locations = locationRepo.getAll();
        List<User> teachers = userRepo.getAllTeachers();

        JsonNode locationJson = Json.toJson(locations);
        JsonNode teacherJson = Json.toJson(teachers);

        Map<String, String> baseValues = mapValuesFromRequest(trainingData);

        List<String> dates = getValues(trainingData, "Date");
        List<String> locationIDs = getValues(trainingData, LOCATION);
        List<String> teacherIDs = getValues(trainingData, TEACHER);
        ArrayList<DateTime> dateTimes = new ArrayList<>();


        if (form.hasErrors()) {
            return badRequest(addtraining.render(form, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), ADDTRAINING, locationRepo.getAll(), userRepo.getAllTeachers(), locationJson, teacherJson));
        } else {
            Training training = form.bind(baseValues).get();
            Form<Training> form2 = form.bind(baseValues);


            if(trainingRepo.getTraining(training.getTrainingCode()) != null) {
                return badRequest(addtraining.render(form2.withError(TRAININGCODE, "Trainingscode moet uniek zijn"), Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), ADDTRAINING, locationRepo.getAll(), userRepo.getAllTeachers(), locationJson, teacherJson));
            }

            List<String> dateIDs = createDates(dates, locationIDs, teacherIDs, training.getDuration());
            training.setDateIds(dateIDs);

            OverlapChecker checker = new OverlapChecker();
            DateTime overlapError;

            for(String dateId : dateIDs) {
                DateTime date = dateRepo.getDateTime(dateId);
                dateTimes.add(date);
                overlapError = detectedOverlap(date, OverlapType.TEACHER);
                if (overlapError != null) {
                    return badRequest(teacheroverlap.render("Error", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), date, overlapError, "/overview"));
                }
            }
            trainingRepo.addTraining(training);
            Training t = trainingRepo.getTraining(training.getTrainingCode());

            for(DateTime date : dateTimes) {
                date.setTrainingID(t.getId());
                dateRepo.updateDateTime(date);
            }
            return ok(message.render(TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Training " + t.getName() + " is aangemaakt", "/managetraining"));
        }
    }

    public Result overview() {
        return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies() ,new ArrayList<>(), null,
                TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null));
    }

    public Result overviewCategory(String category) {
        if(category == null) {
            return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies(),new ArrayList<>(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null));
        } else {
            return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies(), trainingRepo.getTrainingByCategory(category), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null));
        }

    }

    public Result trainingOverview(String category, String id) {
        if (id == null) {
            return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies(), trainingRepo.getTrainingByCategory(category), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null));
        } else {
            Training t = trainingRepo.getTraining(id);

            List<ViewDate> viewDates = new ArrayList<>();

            return ok(trainingoverview.render(trainingRepo.getTrainingFrequencies(), trainingRepo.getTrainingByCategory(category), trainingRepo.getTraining(id),
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), viewDates));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manage() {
        return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), new ArrayList<>(), locationRepo.getAll(), null, TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
    }

    public Result manageCategory(String category) {
        if (category == null) {
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), new ArrayList<>(), locationRepo.getAll(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
        } else {
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manageTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
        } else {
            List<Location> locations = locationRepo.getAll();
            List<User> teachers = userRepo.getAllTeachers();

            JsonNode locationJson = Json.toJson(locations);
            JsonNode teacherJson = Json.toJson(teachers);

            Training t = trainingRepo.getTraining(id);
            List<ViewDate> viewDates = new ArrayList<>();

            getDatesIds(id);


            createViewDates(t, viewDates);


            Form<Training> editForm = form.fill(trainingRepo.getTraining(id));
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(),trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), trainingRepo.getTraining(id),
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), editForm, viewDates, locationJson, teacherJson));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result removeTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), null, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
        } else {
            Training t = trainingRepo.getTraining(id);
            for(String dateId : t.getDateIds()) {
                dateRepo.removeDateTime(dateId);
            }

            sharedRepo.removeTraining(t.getTrainingCode());
            return ok(removetraining.render(t, TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
        }
    }

    public Result removeDate(String id, String trainingCode) {
        dateRepo.removeDateTime(id);
        Training t = trainingRepo.getTraining(trainingCode);
        t.getDateIds().remove(id);
        trainingRepo.updateTraining(t);
        return ok();
    }

    @Security.Authenticated(Secured.class)
    public Result edit(String category, String code) throws ParseException {
        DynamicForm trainingData = formFactory.form().bindFromRequest();

        Map<String, String> baseValues = mapValuesFromRequest(trainingData);

        List<String> dates = getValues(trainingData, "Date");
        List<String> locationIDs = getValues(trainingData, LOCATION);
        List<String> teacherIDs = getValues(trainingData, TEACHER);

        if (trainingData.hasErrors()) {
            flash("danger", "Wrong values");
            return badRequest(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
        } else {
            Training training = form.bind(baseValues).get();
            training.setId(trainingRepo.getTraining(code).getId());
            training.setDateIds(trainingRepo.getTraining(code).getDateIds());

            List<String> requestDateIDs = new ArrayList<>();
            for(int i = 0; i < 50; i++) {
                String d = trainingData.field("DateIDs[" + i + "]").value();
                if(d == null) {
                    break;
                }
                requestDateIDs.add(d);
            }

            List<String> initIDs = training.getDateIds();
            editExistingDates(initIDs, requestDateIDs, dates, locationIDs, teacherIDs);

            if(dates.size() > requestDateIDs.size()) {
                int beginIndex = dates.size() - (dates.size() - requestDateIDs.size());
                DateFormat format = new SimpleDateFormat(DATEFORMAT);

                for(int i = beginIndex; i < dates.size(); i++) {
                    Date date = format.parse(dates.get(i));
                    DateTime dt = new DateTime(date, locationIDs.get(i), teacherIDs.get(i), training.getDuration());

                    DateTime overlapError= detectedOverlap(dt, OverlapType.TEACHER);
                    if(overlapError != null){
                        return badRequest(teacheroverlap.render("Error", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dt, overlapError, "/overview"));
                    }
                    dt.setTrainingID(training.getId());

                    String lastId = dateRepo.addDateTime(dt).toString();
                    training.addDateID(lastId);
                }
            }
            trainingRepo.updateTraining(training);
            return ok(message.render(TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Training " + training.getName() + " is gewijzigd", "/managetraining"));
        }
    }

    private Map<String, String> mapValuesFromRequest(DynamicForm trainingData) {
        Map<String, String> baseValues = new HashMap<>();
        baseValues.put("trainingCode", trainingData.get("trainingCode"));
        baseValues.put("name", trainingData.get("name"));
        baseValues.put("description", trainingData.get("description"));
        baseValues.put("requiredMaterial", trainingData.get("requiredMaterial"));
        baseValues.put("duration", trainingData.get("duration"));
        baseValues.put("tuition", trainingData.get("tuition"));
        baseValues.put("capacity", trainingData.get("capacity"));
        baseValues.put("category", trainingData.get("category"));

        return baseValues;
    }

    private List<String> getValues(DynamicForm trainingData, String type) {
        switch(type) {
            case "Date" :
                return getDates(trainingData);
            case LOCATION :
                return getLocations(trainingData);
            case TEACHER :
                return getTeachers(trainingData);
            default:
                return new ArrayList<>();
        }
    }

    private List<String> getLocations(DynamicForm trainingData) {
        List<String> locations = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            String d = trainingData.field("LocationID[" + i + "]").value();
            if(d == null) {
                break;
            }
            locations.add(d);
        }

        return locations;
    }

    private List<String> getDates(DynamicForm trainingData) {
        List<String> dates = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            String d = trainingData.field("Dates[" + i + "]").value();
            if(d == null) {
                break;
            }
            dates.add(d);
        }

        return dates;
    }

    private List<String> getTeachers(DynamicForm trainingData) {
        List<String> teachers = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            String d = trainingData.field("TeacherID[" + i + "]").value();
            if(d == null) {
                break;
            }
            teachers.add(d);
        }

        return teachers;
    }

    private List<String> createDates(List<String> dates, List<String> locationIDs, List<String> teacherIDs, float duration) throws ParseException {
        List<String> dateIDs = new ArrayList<>();
        String lastId ;

        int counter = 0;

        for(String d : dates) {
            DateFormat format = new SimpleDateFormat(DATEFORMAT);
            Date date = format.parse(d);
            DateTime dt = new DateTime(date, locationIDs.get(counter), teacherIDs.get(counter), duration);
            lastId = dateRepo.addDateTime(dt).toString();
            dateIDs.add(lastId);
            counter++;
        }

        return dateIDs;
    }

    private void getDatesIds(String id) {
        Training t = trainingRepo.getTraining(id);

        List<ViewDate> viewDates = new ArrayList<>();

        int counter = 0;
        for (String dateTime : t.getDateIds()) {
            DateTime d = dateRepo.getDateTime(dateTime);

            Location loc = locationRepo.getLocation(d.getLocationID());
            User teacher = userRepo.getUserByID(d.getTeacherID());
            ViewDate vd = new ViewDate(t.getDateIds().get(counter), d.getDate(), loc, teacher);
            viewDates.add(vd);
            counter++;
        }
    }

    @Security.Authenticated(Secured.class)
    public Result teacherTrainingOverview() {
        List<DateTime> teacherDates = dateRepo.getDateTimeForTeacher(Secured.getUserInfo(ctx()).getId());
        List<ViewTraining> teacherTrainings = new ArrayList<>();
        for (DateTime d : teacherDates)
        {
            ViewTraining vt = new ViewTraining(trainingRepo.getTrainingById(d.getTrainingID()),locationRepo.getLocation(d.getLocationID()),dateRepo.getDateTime(d.getId()));
            teacherTrainings.add(vt);
        }

        return ok(teachertrainingoverview.render(teacherTrainings, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
    }

    private void createViewDates(Training t, List<ViewDate> viewDates) {
        int counter = 0;

        if(!t.getDateIds().isEmpty()) {
            for(String dateTime : t.getDateIds()) {
                DateTime d = dateRepo.getDateTime(dateTime);

                Location loc = locationRepo.getLocation(d.getLocationID());
                User teacher = userRepo.getUserByID(d.getTeacherID());
                ViewDate vd = new ViewDate(t.getDateIds().get(counter), d.getDate(), loc, teacher);
                viewDates.add(vd);
                counter++;
            }
        }
    }



    private void editExistingDates(List<String> initIDs, List<String> requestDateIDs, List<String> dates, List<String> locationIDs, List<String> teacherIDs) throws ParseException {
        int j = 0;

        if(!initIDs.isEmpty()) {
            for(String d : initIDs) {
                if(d.equals(requestDateIDs.get(j))) {
                    DateTime dt = dateRepo.getDateTime(d);

                    DateFormat df = new SimpleDateFormat(DATEFORMAT);
                    Date date = df.parse(dates.get(j));

                    dt.setDate(date);
                    dt.setLocationID(locationIDs.get(j));
                    dt.setTeacherID(teacherIDs.get(j));
                    dateRepo.updateDateTime(dt);
                }
                j++;
            }
        }
    }

    public DateTime detectedOverlap(DateTime signUpDate, OverlapType type){
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

