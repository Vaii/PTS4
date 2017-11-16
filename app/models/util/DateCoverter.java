package models.util;

import dal.contexts.LocationMongoContext;
import dal.contexts.UserMongoContext;
import dal.repositories.LocationRepository;
import dal.repositories.UserRepository;
import models.storage.DateTime;
import models.storage.Location;
import models.storage.User;
import models.view.ViewDate;

public class DateCoverter {
    private UserRepository userRepo = new UserRepository(new UserMongoContext("User"));
    private LocationRepository locationRepo = new LocationRepository(new LocationMongoContext("Location"));

    public ViewDate convert(DateTime dt) {
        User teacher = userRepo.getUserByID(dt.getTeacherID());
        Location location = locationRepo.getLocation(dt.getLocationID());

        return new ViewDate(dt.getId(), dt.getDate(), location, teacher);
    }
}
