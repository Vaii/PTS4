package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTime {
    // Mongo DB identifiers.
    private static final String M_DATE = "Date";
    private static final String M_TRAINEES = "Trainees";
    private static final String M_LOCATION = "LocationID";
    private static final String M_TEACHER = "TeacherID";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    private Date date;

    private List<String> trainees;

    // MongoDB ID of the location.
    private String LocationID;
    private String TeacherID;

    @JsonCreator
    public DateTime(@JsonProperty(M_DATE) Date date,
                    @JsonProperty(M_LOCATION) String LocationID,
                    @JsonProperty(M_TEACHER) String TeacherID,
                    @JsonProperty(M_TRAINEES) List<String> trainees) {
        this.date = date;
        this.LocationID = LocationID;
        this.TeacherID = TeacherID;
        this.trainees = trainees;
    }

    public DateTime(Date date, String LocationID, String TeacherID){
        this.date = date;
        this.LocationID = LocationID;
        this.TeacherID = TeacherID;
        trainees = new ArrayList<>();
    }

    @JsonProperty(M_TRAINEES)
    public List<String> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<String> trainees) {
        this.trainees = trainees;
    }

    @JsonProperty(M_LOCATION)
    public String getLocationID() {
        return LocationID;
    }

    public void setLocationID(String locationID) {
        this.LocationID = locationID;
    }

    @JsonProperty(M_TEACHER)
    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        this.LocationID = teacherID;
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

    public String getDateString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(this.date);
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addTrainee(String id){
        trainees.add(id);
    }

    /**
     * Check overlap with another DateTime object.
     *
     * @param myDuration The duration in the parent training of this DateTime object.
     * @param other The other DateTime object to check overlap with.
     * @param otherDuration The Duration of the parent training of the other DateTime object.
     * @return true if there is a overlap between the 2 durations.
     */
    public boolean checkOverlap(Float myDuration, DateTime other,Float otherDuration ) {

        Calendar c = Calendar.getInstance();
        c.setTime(this.date);
        c.add(Calendar.DATE, myDuration.intValue());
        Date myEndDate = c.getTime();

        Calendar co = Calendar.getInstance();
        co.setTime(other.getDate());
        co.add(Calendar.DATE, otherDuration.intValue());
        Date otherEndDate = co.getTime();

        return other.getDate().after(this.date) && other.getDate().before(myEndDate) ||
                otherEndDate.after(this.date) && otherEndDate.before(myEndDate) ||
                this.date.after(other.getDate()) && this.date.before(otherEndDate);

    }
}
