package models.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class DateTime {
    // Mongo DB identifiers.
    private static final String M_DATE = "date";
    private static final String M_TRAINEES = "trainees";
    private static final String M_LOCATION = "locationId";
    private static final String M_TEACHER = "teacherId";
    private static final String M_TRAINING = "trainingId";
    private static final String M_DURATION = "duration";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // the date on which the training will be held.
    private LocalDateTime date;

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

    public DateTime() {}

    @JsonCreator
    public DateTime(@JsonProperty(M_DATE) LocalDateTime date,
                    @JsonProperty(M_LOCATION) String locationId,
                    @JsonProperty(M_TEACHER) String teacherId,
                    @JsonProperty(M_TRAINEES) List<String> trainees,
                    @JsonProperty(M_TRAINING) String trainingId,
                    @JsonProperty(M_DURATION) float duration){
        this.date = date;
        this.locationID = locationId;
        this.teacherID = teacherId;
        this.trainees = trainees;
        this.trainingID = trainingId;
        this.duration = duration;
    }

    public DateTime(LocalDateTime date, String locationId, String teacherId){
        this.date = date;
        this.locationID = locationId;
        this.teacherID = teacherId;
        trainees = new ArrayList<>();
    }

    public DateTime(LocalDateTime date, String locationId, String teacherId, float duration) {
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

    public String getId() {
        return _id;
    }

    @JsonProperty(M_DATE)
    public LocalDateTime getDate() {
        return date;
    }

    public String getDateString() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(this.date);
    }

    public void setDate(LocalDateTime date) {
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
    public DateTime checkOverlap(DateTime other ) {
        Date convertedDatetime = Date.from(this.date.atZone(ZoneId.systemDefault()).toInstant());
        Date convertedDatetime2 = Date.from(other.getDate().atZone(ZoneId.systemDefault()).toInstant());

        Calendar c = Calendar.getInstance();
        c.setTime(convertedDatetime);
        c.add(Calendar.DATE, (int) duration);
        Date myEndDate = c.getTime();

        Calendar co = Calendar.getInstance();
        co.setTime(convertedDatetime2);
        co.add(Calendar.DATE, (int) other.getDuration());
        Date otherEndDate = co.getTime();

        // Make sure we are not comparing to ourselves.
        if(this._id != null && other.getId() != null) {
            if(Objects.equals(this._id, other._id)) {
                return null;
            }
        }

        if(convertedDatetime2.after(convertedDatetime) && convertedDatetime2.before(myEndDate) ||
                otherEndDate.after(convertedDatetime) && otherEndDate.before(myEndDate) ||
                convertedDatetime.after(convertedDatetime2) && convertedDatetime.before(otherEndDate) ||
                this.date.equals(convertedDatetime2)) {
            return other;
        }

        return null;
    }

    public DateTime checkOverlap(List<DateTime> others) {
        for(DateTime other : others) {
            if(checkOverlap(other) != null) {
                return other;
            }
        }
        return null;
    }
}
