package dal.interfaces;

import models.TuitionForm;
import models.User;

import java.util.List;

public interface TuitionFormContext {
    boolean addForm(TuitionForm form);
    boolean updateForm(TuitionForm form);
    boolean removeForm(TuitionForm form);
    List<TuitionForm> getForm(User manager);
    List<TuitionForm> getForms(User employee);
}