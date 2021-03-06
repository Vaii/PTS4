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
    public DateTime getDateTime(String dateId) {
        return context.getDateTime(dateId);
    }

    @Override
    public List<DateTime> getDateTimeForUser(String userId) {
        return context.getDateTimeForUser(userId);
    }

    @Override
    public List<DateTime> getDateTimeForTeacher(String teacherId) {
        return context.getDateTimeForTeacher(teacherId);
    }

    @Override
    public boolean removeUser(String userId) {
        return context.removeUser(userId);
    }

    @Override
    public boolean removeTeacher(String teacherId) {
        return context.removeTeacher(teacherId);
    }

    @Override
    public List<DateTime> getDateTimeForTraining(String trainingId) {
        return context.getDateTimeForTraining(trainingId);
    }

    @Override
    public List<DateTime> getDateTimeForLocation(String locationId) {
        return context.getDateTimeForLocation(locationId);
    }

    @Override
    public boolean checkUserSignup(String userId, String dateId) {
        return context.checkUserSignup(userId, dateId);
    }

    @Override
    public List<DateTime> getFutureDatesForTraining(String trainingId) {
        return context.getFutureDatesForTraining(trainingId);
    }
}
