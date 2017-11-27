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
import models.storage.Training;
import models.storage.User;
import models.view.ViewDate;

import java.util.ArrayList;
import java.util.List;

public class DateConverter {
    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private LocationRepository locationRepo = new LocationRepository(new LocationMongoContext("Location"));
    private DateTimeRepository dateTimeRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));
    private TrainingRepository trainingRepo = new TrainingRepository(new TrainingMongoContext("Training"));

    public ViewDate convert(DateTime dt) {
        User teacher = userRepo.getUserByID(dt.getTeacherID());
        Location location = locationRepo.getLocation(dt.getLocationID());

        return new ViewDate(dt.getId(), dt.getDate(), location, teacher);
    }

    public List<ViewDate> convert(List<DateTime> dates) {
        List<ViewDate> results = new ArrayList<>();
        for (DateTime date : dates) {
            results.add(convert(date));
        }

        return results;
    }

    public List<ViewDate> converts (List<String> dateIds) {
        List<ViewDate> result = new ArrayList<>();
        for (String id : dateIds) {
            result.add(convert(dateTimeRepo.getDateTime(id)));
        }

        return result;
    }

    public List<ViewDate> getViewDates(String id){
        List<String> ids = new ArrayList<>();
        ids = trainingRepo.getTraining(id).getDateIds();
        return converts(ids);
    }
}
