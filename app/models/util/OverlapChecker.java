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
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));

    public DateTime checkOverlapForTrainee(DateTime other, String userId) {
        List<DateTime> dateTimes;
        dateTimes =  dateRepo.getDateTimeForUser(userId);

        if(other.checkOverlap(dateTimes) != null) {
            return other.checkOverlap(dateTimes);
        }

        return null;
    }

    public DateTime checkOverlapForTeacher(DateTime other, String teacherId) {
        List<DateTime> dateTimes;
        dateTimes = dateRepo.getDateTimeForTeacher(teacherId);

        if(other.checkOverlap(dateTimes) != null) {
            return other.checkOverlap(dateTimes);
        }

        return null;
    }
}
