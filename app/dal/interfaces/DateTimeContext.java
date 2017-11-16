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
}
