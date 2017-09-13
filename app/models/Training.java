package models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.format.Formats;

import java.util.List;


public class Training {
    @MongoObjectId
    private int _id;

    private String TrainingCode, Name, Description, RequiredMaterial;
    private Float Duration, Tuition;
    private int Capacity;
    private Formats.DateTime Date;
    private Location location;
    private User Teacher;
    private List<User> Trainee;
    private List<Training> prerequisites;

    public Training(int _id, String trainingCode, String name, String description, String requiredMaterial, Float duration, Float tuition, int capacity, Formats.DateTime date, Location location, User teacher, List<User> trainee, List<Training> prerequisites) {
        this._id = _id;
        TrainingCode = trainingCode;
        Name = name;
        Description = description;
        RequiredMaterial = requiredMaterial;
        Duration = duration;
        Tuition = tuition;
        Capacity = capacity;
        Date = date;
        this.location = location;
        Teacher = teacher;
        Trainee = trainee;
        this.prerequisites = prerequisites;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTrainingCode() {
        return TrainingCode;
    }

    public void setTrainingCode(String trainingCode) {
        TrainingCode = trainingCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRequiredMaterial() {
        return RequiredMaterial;
    }

    public void setRequiredMaterial(String requiredMaterial) {
        RequiredMaterial = requiredMaterial;
    }

    public Float getDuration() {
        return Duration;
    }

    public void setDuration(Float duration) {
        Duration = duration;
    }

    public Float getTuition() {
        return Tuition;
    }

    public void setTuition(Float tuition) {
        Tuition = tuition;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public Formats.DateTime getDate() {
        return Date;
    }

    public void setDate(Formats.DateTime date) {
        Date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getTeacher() {
        return Teacher;
    }

    public void setTeacher(User teacher) {
        Teacher = teacher;
    }

    public List<User> getTrainee() {
        return Trainee;
    }

    public void setTrainee(List<User> trainee) {
        Trainee = trainee;
    }

    public List<Training> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Training> prerequisites) {
        this.prerequisites = prerequisites;
    }
}
