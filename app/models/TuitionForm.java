package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class TuitionForm {
    // Mongo DB identifiers.
    private static final String M_MANAGER = "Manager";
    private static final String M_EMPLOYEE = "Employee";
    private static final String M_TRAINING = "Training";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    private User Manager, Employee;
    private Training Training;

    public TuitionForm(String _id, User manager, User employee, Training training) {
        this._id = _id;
        this.Manager = manager;
        this.Employee = employee;
        this.Training = training;
    }

    @JsonCreator
    public TuitionForm(@JsonProperty(M_MANAGER) User manager,
                       @JsonProperty(M_EMPLOYEE) User employee,
                       @JsonProperty(M_TRAINING) Training training) {
        this.Manager = manager;
        this.Employee = employee;
        this.Training = training;
    }

    public String get_id() {
        return _id;
    }

    @JsonProperty(M_MANAGER)
    public User getManager() {
        return Manager;
    }

    public void setManager(User manager) {
        Manager = manager;
    }

    @JsonProperty(M_EMPLOYEE)
    public User getEmployee() {
        return Employee;
    }

    public void setEmployee(User employee) {
        Employee = employee;
    }

    @JsonProperty(M_TRAINING)
    public Training getTraining() {
        return Training;
    }

    public void setTraining(Training training) {
        this.Training = training;
    }
}
