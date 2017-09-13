package models;

import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

public class Supplier {

    @MongoObjectId
    private String _id;
    private String name;
    private User Contact;
    private List<User> Teachers;
    private List<Training> trainings;

    public Supplier(String _id, String name, User contact, List<User> teachers, List<Training> trainings) {
        this._id = _id;
        this.name = name;
        Contact = contact;
        Teachers = teachers;
        this.trainings = trainings;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getContact() {
        return Contact;
    }

    public void setContact(User contact) {
        Contact = contact;
    }

    public List<User> getTeachers() {
        return Teachers;
    }

    public void setTeachers(List<User> teachers) {
        Teachers = teachers;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }
}
