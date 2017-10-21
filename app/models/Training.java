package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.validation.Constraints;

import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The training class contains all information for a training.
 */
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
    private static final String M_CATEGORY = "Category";
    private static final String M_LOCATION = "LocationID";
    private static final String M_TEACHER = "TeacherID";
    private static final String M_TRAINEE = "Trainee";
    private static final String M_PREREQUISITES = "Prerequisites";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class identifiers.
    // Training code is a unique identifier defined by the user.
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
    private String Category;

    // MongoDB ID of the location.
    @Constraints.Required
    private String LocationID;
    // MongoDB ID of the teacher.
    private String TeacherID;
    // MongoDB ID's of the users signed up for the training.
    private List<String> Trainee;
    // MongoDB ID's of the trainings that are required to follow before this one.
    private List<String> Prerequisites;

    public Training() {
        this.Trainee = new ArrayList<>();
        this.Prerequisites = new ArrayList<>();
    }

    public Training(String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, Date date, String category) {
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.Date = date;
        this.Category = category;
        this.Trainee = new ArrayList<>();
        this.Prerequisites = new ArrayList<>();
        prepareForStorage();
    }

    public Training(String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, Date date,String category, String locationID) {
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.Date = date;
        this.Category = category;
        this.LocationID = locationID;
        this.Trainee = new ArrayList<>();
        this.Prerequisites = new ArrayList<>();
        prepareForStorage();
    }

    public Training(String _id, String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, Date date, String category, String locationID, String teacherID, List<String> trainee, List<String> prerequisites) {
        this._id = _id;
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.Date = date;
        this.Category = category;
        this.LocationID = locationID;
        this.TeacherID = teacherID;
        this.Trainee = trainee;
        this.Prerequisites = prerequisites;
        prepareForStorage();
    }

    @JsonCreator
    public Training(@JsonProperty(M_TRAININGSCODE) String trainingCode,
                    @JsonProperty(M_NAME) String name,
                    @JsonProperty(M_DESCRIPTION) String description,
                    @JsonProperty(M_REQUIREDMATERIAL) String requiredMaterial,
                    @JsonProperty(M_DURATION) float duration,
                    @JsonProperty(M_TUITION) float tuition,
                    @JsonProperty(M_CAPACITY) int capacity,
                    @JsonProperty(M_DATE) Date date,
                    @JsonProperty(M_CATEGORY) String category,
                    @JsonProperty(M_LOCATION) String locationID,
                    @JsonProperty(M_TEACHER) String teacherID,
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
        this.Category = category;
        this.LocationID = locationID;
        this.TeacherID = teacherID;
        this.Trainee = trainee;
        this.Prerequisites = prerequisites;
        prepareForStorage();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
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
    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
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

    /**
     * Signs up a user for the training.
     *
     * @param id The mongoDB ID of the user to sign up.
     * @return true if the user was added to the training, false otherwise.
     */
    public boolean addTrainee(String id) {
        return Trainee.add(id);
    }

    /**
     * Add a prerequisite for this training.
     * The list is not in any specific order.
     *
     * @param id The mongoDB ID of the training.
     * @return true if the training was added to the list, false otherwise.
     */
    public boolean addPrerequisite(String id) {
        return Prerequisites.add(id);
    }

    /**
     * Get the category of the training.
     * @return The category of the training capitalised.
     */
    @JsonProperty(M_CATEGORY)
    public String getCategory() {
        return StringUtils.capitalize(Category);
    }

    public void setCategory(String category) {
        Category = category.toLowerCase();
    }

    /**
     * Prepare the training for db storage.
     * Turn the training category to lowercase to prevent mix ups.
     */
    private void prepareForStorage() {
        Category = Category.toLowerCase();
    }
}
