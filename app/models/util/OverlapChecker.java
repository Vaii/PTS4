package models.util;

import dal.contexts.DateTimeMongoContext;
import dal.repositories.DateTimeRepository;
import models.storage.DateTime;

import java.util.ArrayList;
import java.util.Date;
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

    public DateTime checkOverlapForTeacher(Date other, int otherDate, String teacherId) {
        List<DateTime> dateTimes;
        dateTimes = dateRepo.getDateTimeForTeacher(teacherId);

        List<DateTime> errorDate = new ArrayList<>();

        for(DateTime dt : dateTimes) {
            DateTime error = dt.checkOverlap(other, otherDate);
            if(error != null) {
                errorDate.add(error);
            }
        }

        if(errorDate.size() > 0) {
            return errorDate.get(0);
        }

        return null;
    }
}
