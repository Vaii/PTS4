package dal.repositories;

import dal.interfaces.TuitionFormContext;
import models.Training;
import models.TuitionForm;
import models.User;

import java.util.List;

public class TuitionFormRepository implements TuitionFormContext {
    private TuitionFormContext context;

    public TuitionFormRepository(TuitionFormContext context) {
        this.context = context;
    }

    @Override
    public boolean addTuitionForm(TuitionForm tuitionForm) {
        return context.addTuitionForm(tuitionForm);
    }

    @Override
    public boolean updateTuitionForm(TuitionForm tuitionForm) {
        return context.updateTuitionForm(tuitionForm);
    }

    @Override
    public boolean removeTuitionForm(TuitionForm tuitionForm) {
        return context.removeTuitionForm(tuitionForm);
    }

    @Override
    public List<TuitionForm> getAll() {
        return context.getAll();
    }

    @Override
    public List<TuitionForm> getTuitionForm(User employees) {
        return context.getTuitionForm(employees);
    }

    @Override
    public List<TuitionForm> getTuitionForm(User manager, User Employee) {
        return context.getTuitionForm(manager, Employee);
    }

    @Override
    public List<TuitionForm> getTuitionForm(Training training) {
        return context.getTuitionForm(training);
    }
}
