package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.validation.Constraints;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Training {
    // Mongo DB identifiers.
    private static final String M_TRAININGSCODE = "TrainingsCode";
    private static final String M_NAME = "Name";
    private static final String M_DESCRIPTION = "Description";
    private static final String M_REQUIREDMATERIAL = "RequiredMaterial";
    private static final String M_DURATION = "Duration";
    private static final String M_TUITION = "Tuition";
    private static final String M_CAPACITY = "Capacity";
    private static final String M_DATE = "Date";
    private static final String M_LOCATION = "LocationID";
    private static final String M_TEACHER = "Teacher";
    private static final String M_TRAINEE = "Trainee";
    private static final String M_PREREQUISITES = "Prerequisites";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class identifiers.
    @Constraints.Required
    private String TrainingCode, Name, Description;
    private String RequiredMaterial;

    @Constraints.Required
    private Float Duration, Tuition;
    @Constraints.Required
    private int Capacity;
    @Constraints.Required
    private java.util.Date Date;

    @Constraints.Required
    private String LocationID;
    private User Teacher;
    private List<String> Trainee;
    private List<String> Prerequisites;

    public Training() {
        this.Trainee = new ArrayList<>();
        this.Prerequisites = new ArrayList<>();
    }

    public Training(String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, Date date) {
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.Date = date;
        this.Trainee = new ArrayList<>();
    }

    public Training(String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, Date date, String locationID) {
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.Date = date;
        this.LocationID = locationID;
    }

    public Training(String _id, String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, Date date, String locationID, User teacher, List<String> trainee, List<String> prerequisites) {
        this._id = _id;
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.Date = date;
        this.LocationID = locationID;
        this.Teacher = teacher;
        this.Trainee = trainee;
        this.Prerequisites = prerequisites;
    }

    @JsonCreator
    public Training(@JsonProperty(M_TRAININGSCODE) String trainingCode,
                    @JsonProperty(M_NAME) String name,
                    @JsonProperty(M_DESCRIPTION) String description,
                    @JsonProperty(M_REQUIREDMATERIAL) String requiredMaterial,
                    @JsonProperty(M_DURATION) float duration,
                    @JsonProperty(M_TUITION) float tuition,
                    @JsonProperty(M_CAPACITY) int capacity,
                    @JsonProperty(M_DATE)Date date,
                    @JsonProperty(M_LOCATION) String locationID,
                    @JsonProperty(M_TEACHER) User teacher,
                    @JsonProperty(M_TRAINEE) List<String> trainee,
                    @JsonProperty(M_PREREQUISITES) List<String> prerequisites) {
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.Date = date;
        this.LocationID = locationID;
        this.Teacher = teacher;
        this.Trainee = trainee;
        this.Prerequisites = prerequisites;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id){
        this._id = _id;
    }

    @JsonProperty(M_TRAININGSCODE)
    public String getTrainingCode() {
        return TrainingCode;
    }

    public void setTrainingCode(String trainingCode) {
        TrainingCode = trainingCode;
    }

    @JsonProperty(M_NAME)
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @JsonProperty(M_DESCRIPTION)
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @JsonProperty(M_REQUIREDMATERIAL)
    public String getRequiredMaterial() {
        return RequiredMaterial;
    }

    public void setRequiredMaterial(String requiredMaterial) {
        RequiredMaterial = requiredMaterial;
    }

    @JsonProperty(M_DURATION)
    public Float getDuration() {
        return Duration;
    }

    public void setDuration(Float duration) {
        Duration = duration;
    }

    @JsonProperty(M_TUITION)
    public Float getTuition() {
        return Tuition;
    }

    public void setTuition(Float tuition) {
        Tuition = tuition;
    }

    @JsonProperty(M_CAPACITY)
    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    @JsonProperty(M_DATE)
    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    @JsonProperty(M_LOCATION)
    public String getLocationID() {
        return LocationID;
    }

    public void setLocationID(String locationID) {
        this.LocationID = locationID;
    }

    @JsonProperty(M_TEACHER)
    public User getTeacher() {
        return Teacher;
    }

    public void setTeacher(User teacher) {
        Teacher = teacher;
    }

    @JsonProperty(M_TRAINEE)
    public List<String> getTrainee() {
        return Trainee;
    }

    public void setTrainee(List<String> trainee) {
        Trainee = trainee;
    }

    @JsonProperty(M_PREREQUISITES)
    public List<String> getPrerequisites() {
        return Prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.Prerequisites = prerequisites;
    }

    public boolean addTrainee (String id){
        return Trainee.add(id);
    }
}
