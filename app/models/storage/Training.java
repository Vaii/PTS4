package models.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.validation.Constraints;

import java.util.ArrayList;
import java.util.List;

/**
 * The training class contains all information for a training.
 */
public class Training  {
    // Mongo DB identifiers.
    private static final String M_TRAININGSCODE = "trainingCode";
    private static final String M_NAME = "name";
    private static final String M_DESCRIPTION = "description";
    private static final String M_REQUIREDMATERIAL = "requiredMaterial";
    private static final String M_DURATION = "duration";
    private static final String M_TUITION = "tuition";
    private static final String M_CAPACITY = "capacity";
    private static final String M_DATEIDS = "dateIds";
    private static final String M_CATEGORY = "category";
    private static final String M_PREREQUISITES = "prerequisites";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class identifiers.
    // Training code is a unique identifier defined by the user.
    @Constraints.Required
    private String trainingCode, name, description;
    private String requiredMaterial;

    @Constraints.Required
    private Float duration, tuition;
    @Constraints.Required
    private int capacity;

    private List<String> dateIds;

    @Constraints.Required
    private String category;

    // MongoDB ID's of the trainings that are required to follow before this one.
    private List<String> prerequisites;

    public Training() {
        this.prerequisites = new ArrayList<>();
    }

    public Training(String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, String category) {
        this.trainingCode = trainingCode;
        this.name = name;
        this.description = description;
        this.requiredMaterial = requiredMaterial;
        this.duration = duration;
        this.tuition = tuition;
        this.capacity = capacity;
        this.category = category;
        this.prerequisites = new ArrayList<>();
        prepareForStorage();
    }

    public Training(String id, String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, String category, List<String> prerequisites) {
        this._id = id;
        this.trainingCode = trainingCode;
        this.name = name;
        this.description = description;
        this.requiredMaterial = requiredMaterial;
        this.duration = duration;
        this.tuition = tuition;
        this.capacity = capacity;
        this.category = category;
        this.prerequisites = prerequisites;
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
        this.trainingCode = trainingCode;
        this.name = name;
        this.description = description;
        this.requiredMaterial = requiredMaterial;
        this.duration = duration;
        this.tuition = tuition;
        this.capacity = capacity;
        this.dateIds = dateIDS;
        this.category = category;
        this.prerequisites = prerequisites;
        prepareForStorage();
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    @JsonProperty(M_TRAININGSCODE)
    public String getTrainingCode() {
        return trainingCode;
    }

    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }

    @JsonProperty(M_NAME)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(M_DESCRIPTION)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty(M_REQUIREDMATERIAL)
    public String getRequiredMaterial() {
        return requiredMaterial;
    }

    public void setRequiredMaterial(String requiredMaterial) {
        this.requiredMaterial = requiredMaterial;
    }

    @JsonProperty(M_DURATION)
    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    @JsonProperty(M_TUITION)
    public Float getTuition() {
        return tuition;
    }

    public void setTuition(Float tuition) {
        this.tuition = tuition;
    }

    @JsonProperty(M_CAPACITY)
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @JsonProperty(M_DATEIDS)
    public List<String> getDateIds() {
        return dateIds;
    }

    public void setDateIds(List<String> dateIds) {
        this.dateIds = dateIds;
    }

    public void addDateID(String dateID) {
        dateIds.add(dateID);
    }

    @JsonProperty(M_PREREQUISITES)
    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    /**
     * Add a prerequisite for this training.
     * The list is not in any specific order.
     *
     * @param id The mongoDB ID of the training.
     * @return true if the training was added to the list, false otherwise.
     */
    public boolean addPrerequisite(String id) {
        return prerequisites.add(id);
    }

    /**
     * Get the category of the training.
     * @return The category of the training capitalised.
     */
    @JsonProperty(M_CATEGORY)
    public String getCategory() {
        return StringUtils.capitalize(category);
    }

    public void setCategory(String category) {
        this.category = category.toLowerCase();
    }

    /**
     * Prepare the training for db storage.
     * Turn the training category to lowercase to prevent mix ups.
     */
    private void prepareForStorage() {
        category = category.toLowerCase();
    }

}
