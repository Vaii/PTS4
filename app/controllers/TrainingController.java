package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import dal.contexts.*;
import dal.repositories.*;
import models.storage.*;
import models.util.DateConverter;
import models.util.OverlapChecker;
import models.util.Redirect;
import models.view.ViewDate;
import models.view.ViewTraining;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;
import sun.util.calendar.LocalGregorianCalendar;
import views.html.shared.message;
import views.html.training.*;
import views.html.teacher.*;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Ken on 27-9-2017.
 */
public class TrainingController extends Controller {

    private static final String LOCATION = "locationId";
    private static final String ADDTRAINING = "Add training";
    private static final String TEACHER = "teacherId";
    private static final String TRAININGCODE = "trainingCode";
    private static final String TRAININGEN = "Trainingen";
    private static final String DATEFORMAT = "yyyy-MM-dd'T'hh:mm";

    private Form<TuitionForm> tuitionFormForm;
    private TuitionFormRepository tutRepo = new TuitionFormRepository(new TuitionFormMongoContext("TuitionForm"));
    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));
    private LocationRepository locationRepo = new LocationRepository(new LocationMongoContext("Location"));
    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    private SharedRepository sharedRepo = new SharedRepository(new SharedMongoContext());
    private CategoryRepository categoryRepo = new CategoryRepository(new CategoryContext("Category"));

    private FormFactory formFactory;
    private Form<Training> form;
    private DateConverter converter = new DateConverter();


    @Inject
    public TrainingController(FormFactory formFactory) {
        this.formFactory = formFactory;
        this.form = formFactory.form(Training.class);
        this.tuitionFormForm = formFactory.form(TuitionForm.class);
    }

    @With(Redirect.class)
    @Security.Authenticated(Secured.class)
    public Result signUpCourse(String id) {
        if (id == null) {
            return notFound();
        } else {
            if (Secured.getUserInfo(ctx()).getRole() != Role.EXTERN) {
                DateTime dt = dateRepo.getDateTime(id);
                List<User> managers = userRepo.getAllManagers();
                Map<String, String> managerMap = mapManager(managers);

                return ok(signUpCourseEmployee.render("Training inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dt, trainingRepo.getTrainingById(dt.getTrainingID()), tuitionFormForm, managerMap));
            } else {
                DateTime signUpDate = dateRepo.getDateTime(id);
                DateTime overlapError = detectedOverlap(signUpDate, OverlapType.STUDENT);

                if (overlapError != null) {
                    return ok(signupError.render("Error", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), trainingRepo.getTrainingById(signUpDate.getTrainingID()), trainingRepo.getTrainingById(overlapError.getTrainingID()), signUpDate, overlapError, "/overview"));
                }

                signUpDate.addTrainee(Secured.getUserInfo(ctx()).getId());
                dateRepo.updateDateTime(signUpDate);
                return ok(message.render("Succesvol Ingeschreven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),
                        "U bent succesvol ingeschreven voor de de training", "/personalOverview"));
            }
        }
    }

    @With(Redirect.class)
    @Security.Authenticated(Secured.class)
    public Result signOutCourse(String id) {
        if (id == null) {
            return notFound();
        } else {

            Date date = new Date();
            DateTime signOutDate = dateRepo.getDateTime(id);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE,7);
            int days = c.getTime().compareTo(signOutDate.getDate());
            if(days<0){
                signOutDate.removeTrainee(Secured.getUserInfo(ctx()).getId());
                dateRepo.updateDateTime(signOutDate);
                return ok(message.render("Succesvol uitgeschreven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),
                        "U bent succesvol uitgeschreven voor de de training", "/"));
            } else {
                return ok(singOutError.render("Uitschrijven mislukt",Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),signOutDate,days));

            }


        }
    }



    @With(Redirect.class)
    @Security.Authenticated(Secured.class)
    public Result signUpEmployee(String id) {

        Form<TuitionForm> filledTuitionForm = tuitionFormForm.bindFromRequest();

        List<User> managers = userRepo.getAllManagers();
        Map<String, String> managerMap = mapManager(managers);

        if (filledTuitionForm.hasErrors()) {
            return badRequest(signUpCourseEmployee.render("Training inschrijven", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dateRepo.getDateTime(id), trainingRepo.getTrainingById(dateRepo.getDateTime(id).getTrainingID()), tuitionFormForm, managerMap));
        } else {
            TuitionForm filledForm = filledTuitionForm.get();
            filledForm.setTotalCosts(filledForm.getStudyCosts() + filledForm.getAccommodationCosts() + filledForm.getExaminationFees() + filledForm.getTransportCosts() + filledForm.getExtraCosts());
            filledForm.setStatus(Status.PENDING);
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

    private Map<String, String> mapManager(List<User> managers){

        Map<String, String> managerMap = new HashMap<>();
        for(User m : managers){
            managerMap.put(m.getId(), m.getEmail());
        }

        return managerMap;
    }

    @Security.Authenticated(Secured.class)
    public Result addTraining() {
        List<Location> locations = locationRepo.getAll();
        List<User> teachers = userRepo.getAllTeachers();
        List<Category> categories = categoryRepo.getAllCategories();

        JsonNode locationJson = Json.toJson(locations);
        JsonNode teacherJson = Json.toJson(teachers);

        return ok(addtraining.render(form, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), ADDTRAINING, locations, teachers, categories, locationJson, teacherJson));
    }

    @Security.Authenticated(Secured.class)
    public Result submit() throws ParseException { // submit new training
        DynamicForm trainingData = formFactory.form().bindFromRequest();

        JsonNode locationJson = Json.toJson(locationRepo.getAll()); // Json for filling dynamic form elements
        JsonNode teacherJson = Json.toJson(userRepo.getAllTeachers());

        Map<String, String> baseValues = mapValuesFromRequest(trainingData);

        List<String> dates = getValuesFromRequest(trainingData, "date");
        List<String> locationIDs = getValuesFromRequest(trainingData, LOCATION);
        List<String> teacherIDs = getValuesFromRequest(trainingData, TEACHER);
        ArrayList<DateTime> dateTimes = new ArrayList<>();


        if (form.hasErrors()) {
            return badRequest(addtraining.render(form, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), ADDTRAINING, locationRepo.getAll(), userRepo.getAllTeachers(), categoryRepo.getAllCategories(), locationJson, teacherJson));
        } else {
            Training training = form.bind(baseValues).get();
            Form<Training> form2 = form.bind(baseValues);


            if(trainingRepo.getTraining(training.getTrainingCode()) != null) {
                return badRequest(addtraining.render(form2.withError(TRAININGCODE, "Trainingscode moet uniek zijn"), Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), ADDTRAINING, locationRepo.getAll(), userRepo.getAllTeachers(), categoryRepo.getAllCategories(), locationJson, teacherJson));
            }

            List<String> dateIDs = createDateTimes(dates, locationIDs, teacherIDs, training.getDuration());
            training.setDateIds(dateIDs);

            DateTime overlapError;

            for(String dateId : dateIDs) {
                DateTime date = dateRepo.getDateTime(dateId);
                dateTimes.add(date);
                overlapError = detectedOverlap(date, OverlapType.TEACHER);
                if (overlapError != null) {
                    for(String dateID : dateIDs) {
                        dateRepo.removeDateTime(dateID);
                    }
                    Training t = trainingRepo.getTrainingsByDate(overlapError.getId()).get(0);
                    return badRequest(teacheroverlap.render("Error", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), date, overlapError, t, "/overview"));
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

    @With(Redirect.class)
    public Result overview() {
        return ok(trainingoverview.render(sharedRepo.getTrainingFrequencies() ,new ArrayList<>(), null,
                TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null, ""));
    }

    public Result trainingOverview(String id) {
        if (id == null) {
            return ok(trainingoverview.render(sharedRepo.getTrainingFrequencies(), new ArrayList<>(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null, ""));
        } else {

            Training t = trainingRepo.getTrainingById(id);
            Category cat = categoryRepo.getCategoryById(t.getCategoryid());
            JsonNode node = Json.toJson(t);

            return ok(trainingoverview.render(sharedRepo.getTrainingFrequencies(), new ArrayList<>(),node ,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), null, cat.getCategory()));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result personalOverview() {
        List<DateTime> trainingDates = dateRepo.getDateTimeForUser(Secured.getUserInfo(ctx()).getId());

        List<ViewTraining> userTrainings = new ArrayList<>();
        DateConverter converter = new DateConverter();
        List<TuitionForm> forms = new ArrayList<>();

        if(!Secured.getUserInfo(ctx()).getRole().equals(Role.EXTERN)){
            forms.addAll(tutRepo.getForms(Secured.getUserInfo(ctx()).getId()));
        }

        for (DateTime d : trainingDates){
            Training t = trainingRepo.getTrainingById(d.getTrainingID());
            ViewTraining training = new ViewTraining(t,converter.convert(d),categoryRepo.getCategoryById(t.getCategoryid()));
            userTrainings.add(training);
        }
        return ok(personaltrainingoverview.render(userTrainings, TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), forms));
    }

    @Security.Authenticated(Secured.class)
    public Result manage() {
        return ok(managetraining.render(sharedRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), new ArrayList<>(), locationRepo.getAll(), categoryRepo.getAllCategories(),null, TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
    }

    public Result manageCategory(String category) {
        if (category == null) {
            return ok(managetraining.render(sharedRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), new ArrayList<>(), locationRepo.getAll(), categoryRepo.getAllCategories(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
        } else {
            String categoryId = categoryRepo.getCategoryByName(category).get_id();
            return ok(managetraining.render(sharedRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(categoryId), locationRepo.getAll(), categoryRepo.getAllCategories(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result manageTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(sharedRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), categoryRepo.getAllCategories(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
        } else {
            List<Location> locations = locationRepo.getAll();
            List<User> teachers = userRepo.getAllTeachers();
            List<Category> categories = categoryRepo.getAllCategories();

            JsonNode locationJson = Json.toJson(locations);
            JsonNode teacherJson = Json.toJson(teachers);

            Training t = trainingRepo.getTrainingById(id);
            Category cat = categoryRepo.getCategoryById(category);
            ViewTraining viewTraining = new ViewTraining(t,null,cat);
            List<ViewDate> viewDates;
            viewDates = converter.getViewDates(id, Secured.getUserInfo(ctx()).getId());

            //Collections.sort(viewDates); this is buggy when editing locations in managetraining

            Form<Training> editForm = form.fill(viewTraining.getTraining());
            return ok(managetraining.render(sharedRepo.getTrainingFrequencies(), userRepo.getAllTeachers(),trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), categories, viewTraining,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), editForm, viewDates, locationJson, teacherJson));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result removeTraining(String category, String id) {
        if (id == null) {
            return ok(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(category), locationRepo.getAll(), categoryRepo.getAllCategories(), null, TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
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
    public Result edit(String categoryid, String code) throws ParseException {
        DynamicForm trainingData = formFactory.form().bindFromRequest();

        Map<String, String> baseValues = mapValuesFromRequest(trainingData);

        List<String> dates = getValuesFromRequest(trainingData, "date");
        List<String> locationIDs = getValuesFromRequest(trainingData, LOCATION);
        List<String> teacherIDs = getValuesFromRequest(trainingData, TEACHER);

        if (trainingData.hasErrors()) {
            return badRequest(managetraining.render(trainingRepo.getTrainingFrequencies(), userRepo.getAllTeachers(), trainingRepo.getTrainingByCategory(categoryid), locationRepo.getAll(), categoryRepo.getAllCategories(), null,
                    TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), form, null, null, null));
        } else {
            Training training = form.bind(baseValues).get();
            training.setId(trainingRepo.getTraining(code).getId());
            training.setDateIds(trainingRepo.getTraining(code).getDateIds());

            // Get dateIds that still exist after editing training
            List<String> remainingDateIDs = getValuesFromRequest(trainingData, "dateIds");

            // Original dateIds
            List<String> initIDs = training.getDateIds();

            for(String id : initIDs) {
                DateTime dt = dateRepo.getDateTime(id);
                DateTime overlapError = detectedOverlap(dt, OverlapType.TEACHER);
                if(overlapError != null){
                    Training t = trainingRepo.getTrainingsByDate(overlapError.getId()).get(0);
                    return badRequest(teacheroverlap.render("Error", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dt, overlapError,t, "/overview"));
                }
            }

            editExistingDates(initIDs, remainingDateIDs, dates, locationIDs, teacherIDs);

            if(dates.size() > remainingDateIDs.size()) { // Some new date(s) are added
                DateTime dt = null;
                DateTime overlapError = null;
                Training t = null;
                if(!addNewDatesToTraining(training, dates, remainingDateIDs, locationIDs, teacherIDs, dt, overlapError, t)) {
                    return badRequest(teacheroverlap.render("Error", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dt, overlapError,t, "/overview"));
                }
            }
            trainingRepo.updateTraining(training);
            return ok(message.render(TRAININGEN, Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), "Training " + training.getName() + " is gewijzigd", "/managetraining"));
        }
    }

    // Put data from request in map, which do NOT have multiple values
    private Map<String, String> mapValuesFromRequest(DynamicForm trainingData) {
        Map<String, String> baseValues = new HashMap<>();
        baseValues.put("trainingCode", trainingData.get("trainingCode"));
        baseValues.put("name", trainingData.get("name"));
        baseValues.put("description", trainingData.get("description"));
        baseValues.put("requiredMaterial", trainingData.get("requiredMaterial"));
        baseValues.put("duration", trainingData.get("duration"));
        baseValues.put("tuition", trainingData.get("tuition"));
        baseValues.put("capacity", trainingData.get("capacity"));
        baseValues.put("categoryid", trainingData.get("categoryid"));

        return baseValues;
    }

    // Put data from request in lists, which have multiple values, for instance dates, teachers etc.
    private List<String> getValuesFromRequest(DynamicForm trainingData, String type) {
        return getSpecificValues(trainingData, type);
    }

    private List<String> getSpecificValues(DynamicForm trainingData, String type) {
        List<String> values = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            String d = trainingData.field(type + "[" + i + "]").value();
            if(d == null) {
                break;
            }
            values.add(d);
        }

        return values;
    }

    private List<String> createDateTimes(List<String> dates, List<String> locationIDs, List<String> teacherIDs, float duration) throws ParseException {
        List<String> dateIDs = new ArrayList<>();
        String lastId ;
        DateFormat format = new SimpleDateFormat(DATEFORMAT);

        int counter = 0;

        for(String d : dates) {
            Date date = format.parse(d);
            DateTime dt = new DateTime(date, locationIDs.get(counter), teacherIDs.get(counter), duration);
            lastId = dateRepo.addDateTime(dt).toString();
            dateIDs.add(lastId);
            counter++;
        }
        return dateIDs;
    }

    @Security.Authenticated(Secured.class)
    public Result teacherTrainingOverview() {
        List<DateTime> teacherDates = dateRepo.getDateTimeForTeacher(Secured.getUserInfo(ctx()).getId());
        List<ViewTraining> teacherTrainings = new ArrayList<>();
        DateConverter converter = new DateConverter();

        for (DateTime d : teacherDates)
        {
            Training t = trainingRepo.getTrainingById(d.getTrainingID());
            ViewTraining vt = new ViewTraining(t, converter.convertWithTrainees(d), categoryRepo.getCategoryById(t.getCategoryid()));
            teacherTrainings.add(vt);
        }

        Collections.sort(teacherTrainings);

        JsonNode dateJson = Json.toJson(teacherTrainings);


        return ok(teachertrainingoverview.render(teacherTrainings, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), dateJson));
    }

    @Security.Authenticated(Secured.class)
    public Result teacherStudentOverview(String dateId) {
        DateConverter converter = new DateConverter();
        DateTime date = dateRepo.getDateTime(dateId);
        Training train = trainingRepo.getTrainingById(date.getTrainingID());
        ViewTraining training = new ViewTraining(train,converter.convertWithTrainees(date), categoryRepo.getCategoryById(train.getCategoryid()));

        return ok(teacherstudentoverview.render(training, "Trainingen", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()),converter.convertWithTrainees(date).getTrainees()));
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

    private boolean addNewDatesToTraining(Training training, List<String> dates, List<String> remainingDateIds, List<String> locationIDs, List<String> teacherIDs, DateTime dt, DateTime overlapError, Training t) throws ParseException {
        int beginIndex = dates.size() - (dates.size() - remainingDateIds.size());
        DateFormat format = new SimpleDateFormat(DATEFORMAT);
        DateTimeFormatter f = DateTimeFormatter.ofPattern(DATEFORMAT);

        for(int i = beginIndex; i < dates.size(); i++) {
            Date date = format.parse(dates.get(i));
            dt = new DateTime(date, locationIDs.get(i), teacherIDs.get(i), training.getDuration());
            overlapError = detectedOverlap(dt, OverlapType.TEACHER);

            if(overlapError != null){
                t = trainingRepo.getTrainingsByDate(overlapError.getId()).get(0);
                return false;
            }

            dt.setTrainingID(training.getId());

            String lastId = dateRepo.addDateTime(dt).toString();
            training.addDateID(lastId);
        }

        return true;
    }
}

