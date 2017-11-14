package models.util;

import dal.contexts.DateTimeMongoContext;
import dal.contexts.SharedMongoContext;
import dal.repositories.DateTimeRepository;
import dal.repositories.SharedRepository;
import models.storage.DateTime;
import models.storage.Training;

import java.util.ArrayList;
import java.util.List;

public class OverlapChecker {
    private SharedRepository sharedRepo = new SharedRepository(new SharedMongoContext());
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));

    public boolean checkOverlapForTrainee(DateTime other, String userId) {
        List<DateTime> dateTimes = new ArrayList<>();
        dateTimes =  dateRepo.getDateTimeForUser(userId);

        return other.checkOverlap(dateTimes);
    }

    public boolean checkOverlapForTeacher(DateTime other, String teacherId) {
        List<DateTime> dateTimes = new ArrayList<>();
        dateTimes = dateRepo.getDateTimeForTeacher(teacherId);

        return other.checkOverlap(dateTimes);
    }
}
