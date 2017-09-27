package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

public class Supplier {
    // Mongo DB identifiers.
    private static final String M_NAME = "Name";
    private static final String M_CONTACT = "Contact";
    private static final String M_TEACHERS = "Teachers";
    private static final String M_TRAININGS = "Trainings";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    private String Name;
    private User Contact;
    private List<User> Teachers;
    private List<Training> Trainings;

    public Supplier(String _id, String name, User contact, List<User> teachers, List<Training> trainings) {
        this._id = _id;
        this.Name = name;
        this.Contact = contact;
        this.Teachers = teachers;
        this.Trainings = trainings;
    }

    @JsonCreator
    public Supplier(@JsonProperty(M_NAME) String name,
                    @JsonProperty(M_CONTACT) User contact,
                    @JsonProperty(M_TEACHERS) List<User> teachers,
                    @JsonProperty(M_TRAININGS) List<Training> trainings) {
        this.Name = name;
        this.Contact = contact;
        this.Teachers = teachers;
        this.Trainings = trainings;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @JsonProperty(M_NAME)
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    @JsonProperty(M_CONTACT)
    public User getContact() {
        return Contact;
    }

    public void setContact(User contact) {
        Contact = contact;
    }

    @JsonProperty(M_TEACHERS)
    public List<User> getTeachers() {
        return Teachers;
    }

    public void setTeachers(List<User> teachers) {
        Teachers = teachers;
    }

    @JsonProperty(M_TRAININGS)
    public List<Training> getTrainings() {
        return Trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.Trainings = trainings;
    }
}
