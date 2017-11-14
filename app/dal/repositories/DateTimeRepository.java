package dal.repositories;

import dal.interfaces.DateTimeContext;
import models.storage.DateTime;

import java.util.List;

public class DateTimeRepository implements DateTimeContext {
    private DateTimeContext context;

    public DateTimeRepository(DateTimeContext context) {
        this.context = context;
    }

    @Override
    public Object addDateTime(DateTime dateTime) {
        return context.addDateTime(dateTime);
    }

    @Override
    public boolean updateDateTime(DateTime dateTime) {
        return context.updateDateTime(dateTime);
    }

    @Override
    public boolean removeDateTime(String dateTimeId) {
        return context.removeDateTime(dateTimeId);
    }

    @Override
    public DateTime getDateTime(String date_id) {
        return context.getDateTime(date_id);
    }

    @Override
    public List<DateTime> getDateTimeForUser(String userId) {
        return context.getDateTimeForUser(userId);
    }

    @Override
    public List<DateTime> getDateTimeForTeacher(String teacherId) {
        return context.getDateTimeForTeacher(teacherId);
    }
}
