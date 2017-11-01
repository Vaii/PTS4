package dal.repositories;

import dal.interfaces.DateTimeContext;
import models.DateTime;

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
    public boolean removeDateTime(DateTime dateTime) {
        return context.removeDateTime(dateTime);
    }

    @Override
    public DateTime getDateTime(String date_id) {
        return context.getDateTime(date_id);
    }
}
