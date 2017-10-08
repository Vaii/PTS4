package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Location {
    // Mongo DB identifiers.
    private static final String M_CITY = "City";
    private static final String M_STREETNAME = "StreetName";
    private static final String M_STREETNUMBER = "StreetNumber";
    private static final String M_ROOM = "Room";
    private static final String M_CAPACITY = "Capacity";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    private String City;
    private String StreetName;
    private String StreetNumber;
    private String Room;
    private int Capacity;

    public Location(){

    }

    public Location(String _id, String city, String streetName, String streetNumber, String room, int capacity) {
        this._id = _id;
        City = city;
        StreetName = streetName;
        StreetNumber = streetNumber;
        Room = room;
        Capacity = capacity;
    }

    @JsonCreator
    public Location(@JsonProperty(M_CITY) String city,
                    @JsonProperty(M_STREETNAME) String streetName,
                    @JsonProperty(M_STREETNUMBER) String streetNumber,
                    @JsonProperty(M_ROOM) String room,
                    @JsonProperty(M_CAPACITY) int capacity) {
        this.City = city;
        this.StreetName = streetName;
        this.StreetNumber = streetNumber;
        this.Room = room;
        this.Capacity = capacity;
    }


    public String get_id() {
        return _id;
    }

    @JsonProperty(M_CITY)
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    @JsonProperty(M_STREETNAME)
    public String getStreetName() { return StreetName; }

    public void setStreetName(String streetName) { StreetName = streetName; }

    @JsonProperty(M_STREETNUMBER)
    public String getStreetNumber() {
        return StreetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        StreetNumber = streetNumber;
    }

    @JsonProperty(M_ROOM)
    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    @JsonProperty(M_CAPACITY)
    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }
}

