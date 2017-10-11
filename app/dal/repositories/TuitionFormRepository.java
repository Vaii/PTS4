package dal.repositories;

import dal.interfaces.TuitionFormContext;
import models.TuitionForm;
import models.User;

import java.util.List;

public class TuitionFormRepository implements TuitionFormContext {

    private TuitionFormContext context;

    public TuitionFormRepository(TuitionFormContext context) {
        this.context = context;
    }

    @Override
    public boolean addForm(TuitionForm form) {
        return context.addForm(form);
    }

    @Override
    public boolean updateForm(TuitionForm form) {
        return context.updateForm(form);
    }

    @Override
    public boolean removeForm(TuitionForm form) {
        return context.removeForm(form);
    }

    @Override
    public List<TuitionForm> getForm(String managerID) {
        return context.getForm(managerID);
    }

    @Override
    public List<TuitionForm> getForms(String employeeID) {
        return context.getForms(employeeID);
    }
}
