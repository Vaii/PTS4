package models.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.validation.Constraints;

/**
 * The location class represents data for a real-life location.
 * These location can be linked to a training.
 *
 * @see Training
 */
public class Location {
    // Mongo DB identifiers.
    private static final String M_CITY = "city";
    private static final String M_STREETNAME = "streetName";
    private static final String M_STREETNUMBER = "streetNumber";
    private static final String M_ROOM = "room";
    private static final String M_CAPACITY = "capacity";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    @Constraints.Required
    private String city;
    @Constraints.Required
    private String streetName;
    @Constraints.Required
    private String streetNumber;
    @Constraints.Required
    private String room;
    @Constraints.Required
    private int capacity;

    public Location() {

    }

    public Location(String id, String city, String streetName, String streetNumber, String room, int capacity) {
        this._id = id;
        this.city = city;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.room = room;
        this.capacity = capacity;
    }

    @JsonCreator
    public Location(@JsonProperty(M_CITY) String city,
                    @JsonProperty(M_STREETNAME) String streetName,
                    @JsonProperty(M_STREETNUMBER) String streetNumber,
                    @JsonProperty(M_ROOM) String room,
                    @JsonProperty(M_CAPACITY) int capacity) {
        this.city = city;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.room = room;
        this.capacity = capacity;
    }


    public String getId() {
        return _id;
    }

    @JsonProperty(M_CITY)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty(M_STREETNAME)
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @JsonProperty(M_STREETNUMBER)
    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    @JsonProperty(M_ROOM)
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @JsonProperty(M_CAPACITY)
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

