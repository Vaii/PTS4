package dal.interfaces;

import models.DateTime;

import java.util.List;

public interface DateTimeContext {
    Object addDateTime(DateTime dateTime);
    boolean updateDateTime(DateTime dateTime);
    boolean removeDateTime(String dateTimeId);
    DateTime getDateTime(String date_id);
}
