package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

/**
 * A supplier can offer external trainings to the Info Support network.
 * The class contains all relevant contact information as well as the trainings provided by the supplier.
 */
public class Supplier {
    // Mongo DB identifiers.
    private static final String M_NAME = "Name";
    private static final String M_CONTACT = "ContactID";
    private static final String M_TEACHERS = "Teachers";
    private static final String M_TRAININGS = "Trainings";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    private String Name;
    // MongoDB ID of the contact user.
    private String ContactID;

    // MongoDB ID's of the users.
    private List<String> Teachers;

    // MongoDB iD's of the trainings.
    private List<String> Trainings;

    public Supplier(String _id, String name, String contactID, List<String> teachers, List<String> trainings) {
        this._id = _id;
        this.Name = name;
        this.ContactID = contactID;
        this.Teachers = teachers;
        this.Trainings = trainings;
    }

    @JsonCreator
    public Supplier(@JsonProperty(M_NAME) String name,
                    @JsonProperty(M_CONTACT) String contactID,
                    @JsonProperty(M_TEACHERS) List<String> teachers,
                    @JsonProperty(M_TRAININGS) List<String> trainings) {
        this.Name = name;
        this.ContactID = contactID;
        this.Teachers = teachers;
        this.Trainings = trainings;
    }

    public String get_id() {
        return _id;
    }

    @JsonProperty(M_NAME)
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    @JsonProperty(M_CONTACT)
    public String getContactID() {
        return ContactID;
    }

    public void setContactID(String contactID) {
        ContactID = contactID;
    }

    @JsonProperty(M_TEACHERS)
    public List<String> getTeachers() {
        return Teachers;
    }

    public void setTeachers(List<String> teachers) {
        Teachers = teachers;
    }

    @JsonProperty(M_TRAININGS)
    public List<String> getTrainings() {
        return Trainings;
    }

    public void setTrainings(List<String> trainings) {
        this.Trainings = trainings;
    }
}
