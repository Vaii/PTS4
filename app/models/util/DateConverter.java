package models.util;

import dal.contexts.DateTimeMongoContext;
import dal.contexts.LocationMongoContext;
import dal.contexts.TrainingMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.LocationRepository;
import dal.repositories.TrainingRepository;
import dal.repositories.UserRepository;
import models.storage.DateTime;
import models.storage.Location;
import models.storage.User;
import models.view.ViewDate;

import java.util.ArrayList;
import java.util.List;

public class DateConverter {
    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private LocationRepository locationRepo = new LocationRepository(new LocationMongoContext("Location"));
    private DateTimeRepository dateTimeRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));

    public ViewDate convert(DateTime dt, String userId) {
        User teacher = userRepo.getUserByID(dt.getTeacherID());
        Location location = locationRepo.getLocation(dt.getLocationID());

        return new ViewDate(dt.getId(), dt.getDate(), location, teacher, dt.getTrainees().size(), dt.getTrainees().contains(userId));
    }

    public ViewDate convert(DateTime dt) {
        User teacher = userRepo.getUserByID(dt.getTeacherID());
        Location location = locationRepo.getLocation(dt.getLocationID());

        return new ViewDate(dt.getId(), dt.getDate(), location, teacher, dt.getTrainees().size(), false);
    }

    public ViewDate convertWithTrainees(DateTime dt) {
        User teacher = userRepo.getUserByID(dt.getTeacherID());
        Location location = locationRepo.getLocation(dt.getLocationID());
        List<User> trainees = new ArrayList<>();
        for (String trainee : dt.getTrainees()){
           trainees.add(userRepo.getUserByID(trainee));
        }

        return new ViewDate(dt.getId(), dt.getDate(), location, teacher, dt.getTrainees().size(), trainees);
    }

    public ViewDate convertNoTeacher(DateTime dt, String userId) {
        Location location = locationRepo.getLocation(dt.getLocationID());

        return new ViewDate(dt.getId(), dt.getDate(), location, null, dt.getTrainees().size(), dt.getTrainees().contains(userId));
    }

    public List<ViewDate> convert(List<DateTime> dates) {
        List<ViewDate> results = new ArrayList<>();
        for (DateTime date : dates) {
            results.add(convert(date));
        }
        return results;
    }

    public List<ViewDate> convert(List<DateTime> dates, String userId) {
        List<ViewDate> results = new ArrayList<>();
        for (DateTime date : dates) {
            results.add(convert(date, userId));
        }
        return results;
    }

    public List<ViewDate> converts (List<String> dateIds, String userId) {
        List<ViewDate> result = new ArrayList<>();
        for (String id : dateIds) {
            result.add(convert(dateTimeRepo.getDateTime(id), userId));
        }
        return result;
    }

    public List<ViewDate> convertsNoTeacher (List<DateTime> dateIds, String userId) {
        List<ViewDate> result = new ArrayList<>();
        for (DateTime date : dateIds) {
            result.add(convertNoTeacher(date, userId));
        }
        return result;
    }

    public List<ViewDate> getViewDates(String trainingId, String userId){
        return convert(dateTimeRepo.getDateTimeForTraining(trainingId), userId);
    }

    public List<ViewDate>  getViewDatesWithoutTeacher(String trainingId, String userId, boolean futureOnly) {
        if(futureOnly) {
            return convertsNoTeacher(dateTimeRepo.getFutureDatesForTraining(trainingId), userId);
        } else {
            return convertsNoTeacher(dateTimeRepo.getDateTimeForTraining(trainingId), userId);
        }
    }

    public List<ViewDate> getViewDatesFromUser(String userId){
       return convert(dateTimeRepo.getDateTimeForUser(userId));
    }
}
