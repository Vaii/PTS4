package models.view;

import models.storage.DateTime;
import models.storage.Training;
import models.storage.TuitionForm;
import models.storage.User;

public class ViewTuition {

    private String formId;
    private TuitionForm form;
    private User employee;
    private User manager;
    private DateTime dateTime;
    private Training training;

    public ViewTuition(String formId, TuitionForm form, User employee, User manager, DateTime dateTime, Training training){
        this.formId = formId;
        this.form = form;
        this.employee = employee;
        this.manager = manager;
        this.dateTime = dateTime;
        this.training = training;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public TuitionForm getForm() {
        return form;
    }

    public void setForm(TuitionForm form) {
        this.form = form;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
