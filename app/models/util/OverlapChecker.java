package models.util;

import dal.contexts.DateTimeMongoContext;
import dal.repositories.DateTimeRepository;
import models.storage.DateTime;

import java.util.List;

public class OverlapChecker {
    private DateTimeRepository dateRepo = new DateTimeRepository(new DateTimeMongoContext("DateTime"));

    public DateTime checkOverlapForTrainee(DateTime other, String userId) {
        List<DateTime> dateTimes;
        dateTimes =  dateRepo.getDateTimeForUser(userId);

        DateTime dateError = other.checkOverlap(dateTimes);

        if(dateError != null) {
            return dateError;
        }

        return null;
    }

    public DateTime checkOverlapForTeacher(DateTime other, String teacherId) {
        List<DateTime> dateTimes;
        dateTimes = dateRepo.getDateTimeForTeacher(teacherId);

        DateTime errorDate = other.checkOverlap(dateTimes);

        if(errorDate != null) {
            return errorDate;
        }

        return null;
    }
}
