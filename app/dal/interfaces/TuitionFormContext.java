package dal.interfaces;

import models.Training;
import models.TuitionForm;
import models.User;

import java.util.List;

public interface TuitionFormContext {
    boolean addTuitionForm(TuitionForm tuitionForm);
    boolean updateTuitionForm(TuitionForm tuitionForm);
    boolean removeTuitionForm(TuitionForm tuitionForm);
    List<TuitionForm> getAll();
    List<TuitionForm> getTuitionForm(User employees);
    List<TuitionForm> getTuitionForm(User manager, User Employee);
    List<TuitionForm> getTuitionForm(Training training);
}
