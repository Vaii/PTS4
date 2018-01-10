package models.util;

import dal.contexts.DateTimeMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.TrainingRepository;
import dal.repositories.UserRepository;
import models.storage.DateTime;
import models.storage.Training;
import models.storage.TuitionForm;
import models.storage.User;
import models.view.ViewTuition;

import javax.swing.text.html.FormView;

public class FormConverter {

    private UserRepository uRepo = new UserRepository(new UserMongoContext("User"));
    private DateTimeRepository dRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    private TrainingRepository tRepo = new TrainingRepository(new TrainingMongoContext("Training"));


    public ViewTuition convertForm(TuitionForm form){
        User employee = uRepo.getUserByID(form.getEmployeeID());
        User manager = uRepo.getUserByID(form.getManager());
        DateTime dateTime = dRepo.getDateTime(form.getDateTimeID());
        Training training = tRepo.getTrainingById(dateTime.getTrainingID());

        return new ViewTuition(form.getId(), form, employee, manager, dateTime, training);
    }
}
