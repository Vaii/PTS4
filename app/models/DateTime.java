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
    private static final String M_LOCATION = "locationID";
    private static final String M_TEACHER = "teacherID";
    private static final String M_TRAINING = "trainingID";
    private static final String M_DURATION = "duration";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // the date on which the training will be held.
    private Date date;

    // List of id's from users that have signed up for this training.
    private List<String> trainees;

    // MongoDB ID of the location.
    private String locationID;

    // The UserId of the teacher that will be lecturing.
    private String teacherID;

    // the parent training of this DateTime object.
    private String trainingID;

    // the duration of the training in days.
    // Duplicate data can be found in training.
    // Is also added here to prevent the need of getting an entire training object to do overlap checking.
    private float duration;

    @JsonCreator
    public DateTime(@JsonProperty(M_DATE) Date date,
                    @JsonProperty(M_LOCATION) String LocationID,
                    @JsonProperty(M_TEACHER) String TeacherID,
                    @JsonProperty(M_TRAINEES) List<String> trainees,
                    @JsonProperty(M_TRAINING) String trainingId,
                    @JsonProperty(M_DURATION) float duration){
        this.date = date;
        this.locationID = LocationID;
        this.teacherID = TeacherID;
        this.trainees = trainees;
        this.trainingID = trainingId;
        this.duration = duration;
    }

    public DateTime(Date date, String LocationID, String TeacherID){
        this.date = date;
        this.locationID = LocationID;
        this.teacherID = TeacherID;
        trainees = new ArrayList<>();
    }

    public DateTime(Date date, String locationId, String teacherId, float duration) {
        this.date = date;
        this.locationID = locationId;
        this.teacherID = teacherId;
        this.duration = duration;
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
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    @JsonProperty(M_TEACHER)
    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
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
        return df.format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addTrainee(String id){
        trainees.add(id);
    }

    @JsonProperty(M_TRAINING)
    public String getTrainingID() {
        return trainingID;
    }

    public void setTrainingID(String trainingID) {
        this.trainingID = trainingID;
    }

    @JsonProperty(M_DURATION)
    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * Check overlap with another DateTime object.
     *
     * @param other The other DateTime object to check overlap with.
     * @return true if there is a overlap between the 2 durations.
     */
    public boolean checkOverlap(DateTime other ) {

        Calendar c = Calendar.getInstance();
        c.setTime(this.date);
        c.add(Calendar.DATE, (int) duration);
        Date myEndDate = c.getTime();

        Calendar co = Calendar.getInstance();
        co.setTime(other.getDate());
        co.add(Calendar.DATE, (int) other.getDuration());
        Date otherEndDate = co.getTime();

        return other.getDate().after(this.date) && other.getDate().before(myEndDate) ||
                otherEndDate.after(this.date) && otherEndDate.before(myEndDate) ||
                this.date.after(other.getDate()) && this.date.before(otherEndDate) ||
                this.date.equals(other.getDate());
    }
}
