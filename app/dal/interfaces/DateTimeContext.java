package dal.interfaces;

import models.storage.DateTime;

import java.util.List;

public interface DateTimeContext {
    Object addDateTime(DateTime dateTime);
    boolean updateDateTime(DateTime dateTime);
    boolean removeDateTime(String dateTimeId);
    DateTime getDateTime(String dateId);
    List<DateTime> getDateTimeForUser(String userId);
    List<DateTime> getDateTimeForTeacher(String teacherId);
    boolean removeUser(String userId);
    boolean removeTeacher(String teacherId);
    List<DateTime> getDateTimeForTraining(String trainingId);
    List<DateTime> getDateTimeForLocation(String locationId);
    boolean checkUserSignup(String userId, String dateId);
}
