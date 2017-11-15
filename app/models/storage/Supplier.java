package models.storage;

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
    private static final String M_NAME = "name";
    private static final String M_CONTACT = "contactId";
    private static final String M_TEACHERS = "teachers";
    private static final String M_TRAININGS = "trainings";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    private String name;
    // MongoDB ID of the contact user.
    private String contactId;

    // MongoDB ID's of the users.
    private List<String> teachers;
    // MongoDB iD's of the trainings.
    private List<String> trainings;

    public Supplier(String id, String name, String contactId, List<String> teachers, List<String> trainings) {
        this._id = id;
        this.name = name;
        this.contactId = contactId;
        this.teachers = teachers;
        this.trainings = trainings;
    }

    @JsonCreator
    public Supplier(@JsonProperty(M_NAME) String name,
                    @JsonProperty(M_CONTACT) String contactId,
                    @JsonProperty(M_TEACHERS) List<String> teachers,
                    @JsonProperty(M_TRAININGS) List<String> trainings) {
        this.name = name;
        this.contactId = contactId;
        this.teachers = teachers;
        this.trainings = trainings;
    }

    public String getId() {
        return _id;
    }

    @JsonProperty(M_NAME)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(M_CONTACT)
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @JsonProperty(M_TEACHERS)
    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    @JsonProperty(M_TRAININGS)
    public List<String> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<String> trainings) {
        this.trainings = trainings;
    }
}
