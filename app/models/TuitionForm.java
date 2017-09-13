package models;

import org.jongo.marshall.jackson.oid.MongoObjectId;

public class TuitionForm {
    @MongoObjectId
    private String _id;
    private User Manager, Employee;
    private Training training;

    public TuitionForm(String _id, User manager, User employee, Training training) {
        this._id = _id;
        Manager = manager;
        Employee = employee;
        this.training = training;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getManager() {
        return Manager;
    }

    public void setManager(User manager) {
        Manager = manager;
    }

    public User getEmployee() {
        return Employee;
    }

    public void setEmployee(User employee) {
        Employee = employee;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
