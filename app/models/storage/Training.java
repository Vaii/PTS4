package models.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.validation.Constraints;

import javax.validation.Constraint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The training class contains all information for a training.
 */
public class Training  {
    // Mongo DB identifiers.
    private static final String M_TRAININGSCODE = "TrainingsCode";
    private static final String M_NAME = "Name";
    private static final String M_DESCRIPTION = "Description";
    private static final String M_REQUIREDMATERIAL = "RequiredMaterial";
    private static final String M_DURATION = "Duration";
    private static final String M_TUITION = "Tuition";
    private static final String M_CAPACITY = "Capacity";
    private static final String M_DATEIDS = "DateIDs";
    private static final String M_CATEGORY = "Category";
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

    private List<String> DateIDs;

    @Constraints.Required
    private String Category;

    // MongoDB ID's of the trainings that are required to follow before this one.
    private List<String> Prerequisites;

    public Training() {
        this.Prerequisites = new ArrayList<>();
    }

    public Training(String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, String category) {
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.Category = category;
        this.Prerequisites = new ArrayList<>();
        prepareForStorage();
    }

    public Training(String _id, String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, String category, List<String> prerequisites) {
        this._id = _id;
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.Category = category;
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
                    @JsonProperty(M_DATEIDS) List<String> dateIDS,
                    @JsonProperty(M_CATEGORY) String category,
                    @JsonProperty(M_PREREQUISITES) List<String> prerequisites) {
        this.TrainingCode = trainingCode;
        this.Name = name;
        this.Description = description;
        this.RequiredMaterial = requiredMaterial;
        this.Duration = duration;
        this.Tuition = tuition;
        this.Capacity = capacity;
        this.DateIDs = dateIDS;
        this.Category = category;
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

    @JsonProperty(M_DATEIDS)
    public List<String> getDateIDs() {
        return DateIDs;
    }

    public void setDateIDs(List<String> dateIDs) {
        DateIDs = dateIDs;
    }

    public void addDateID(String dateID) {
        DateIDs.add(dateID);
    }

    @JsonProperty(M_PREREQUISITES)
    public List<String> getPrerequisites() {
        return Prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.Prerequisites = prerequisites;
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