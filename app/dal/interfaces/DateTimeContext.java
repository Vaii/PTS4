package dal.interfaces;

import models.DateTime;

public interface DateTimeContext {
    Object addDateTime(DateTime dateTime);
    boolean updateDateTime(DateTime dateTime);
    boolean removeDateTime(DateTime dateTime);
    DateTime getDateTime(String date_id);
}
