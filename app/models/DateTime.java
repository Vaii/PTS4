package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;
import java.util.List;

public class DateTime {
    // Mongo DB identifiers.
    private static final String M_DATE = "Date";
    private static final String M_TRAINEES = "Trainees";
    private static final String M_LOCATION = "LocationID";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    private Date date;
    private List<String> trainees;

    // MongoDB ID of the location.
    private String LocationID;

    @JsonCreator
    public DateTime(@JsonProperty(M_DATE) Date date, @JsonProperty(M_LOCATION) String LocationID) {
        this.date = date;
        this.LocationID = LocationID;
    }

    @JsonProperty(M_LOCATION)
    public String getLocationID() {
        return LocationID;
    }

    public void setLocationID(String locationID) {
        this.LocationID = locationID;
    }

    public boolean signUp(String userID) {
        return this.trainees.add(userID);
    }

    public String get_id() {
        return _id;
    }

    @JsonProperty(M_DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




}
