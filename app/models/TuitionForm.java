package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

/**
 * The tuition form is used internally by Info Support.
 * When an employee signs up for a course this form will need to be filled out and send to his manager.
 * <p>
 * The manager can then approve or deny his request.
 */
public class TuitionForm {
    // Mongo DB identifiers.
    private static final String M_MANAGER = "Manager";
    private static final String M_EMPLOYEE = "Employee";
    private static final String M_TRAINING = "Training";
    private static final String M_COMPANYJOINDATE = "CompanyJoinDate";
    private static final String M_REASONFORCOURSE = "ReasonForCourse";
    private static final String M_STUDYCOSTS = "StudyCosts";
    private static final String M_EXAMINATIONFEES = "ExaminationFees";
    private static final String M_TRANSPORTCOSTS = "TransportCosts";
    private static final String M_ACCOMODATIONCOSTS = "AccommodationCosts";
    private static final String M_EXTRACOSTS = "ExtraCosts";
    private static final String M_TOTALCOSTS = "TotalCosts";
    private static final String M_CATEGORY = "Category";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    // MongoDB ID's of both users.
    @play.data.validation.Constraints.Required
    private String Manager, Employee;
    // MongoDB ID of the training.
    @play.data.validation.Constraints.Required
    private String Training;
    @play.data.validation.Constraints.Required
    private Date CompanyJoinDate;
    @play.data.validation.Constraints.Required
    private String ReasonForCourse;
    @play.data.validation.Constraints.Required
    private double StudyCosts;
    @play.data.validation.Constraints.Required
    private double ExaminationFees;
    @play.data.validation.Constraints.Required
    private double TransportCosts;
    @play.data.validation.Constraints.Required
    private double AccommodationCosts;
    @play.data.validation.Constraints.Required
    private double ExtraCosts;
    @play.data.validation.Constraints.Required
    private double TotalCosts;
    @play.data.validation.Constraints.Required
    private TuitionCategory Category;

    @JsonCreator
    public TuitionForm(@JsonProperty(M_MANAGER) String manager,
                       @JsonProperty(M_EMPLOYEE) String employee,
                       @JsonProperty(M_TRAINING) String training,
                       @JsonProperty(M_COMPANYJOINDATE) Date companyJoinDate,
                       @JsonProperty(M_REASONFORCOURSE) String reasonForCourse,
                       @JsonProperty(M_STUDYCOSTS) double studyCosts,
                       @JsonProperty(M_EXAMINATIONFEES) double examinationFees,
                       @JsonProperty(M_TRANSPORTCOSTS) double transportCosts,
                       @JsonProperty(M_ACCOMODATIONCOSTS) double accommodationCosts,
                       @JsonProperty(M_EXTRACOSTS) double extraCosts,
                       @JsonProperty(M_TOTALCOSTS) double totalCosts,
                       @JsonProperty(M_CATEGORY) TuitionCategory category) {
        this.Manager = manager;
        this.Employee = employee;
        this.Training = training;
        this.CompanyJoinDate = companyJoinDate;
        this.ReasonForCourse = reasonForCourse;
        this.StudyCosts = studyCosts;
        this.ExaminationFees = examinationFees;
        this.TransportCosts = transportCosts;
        this.AccommodationCosts = accommodationCosts;
        this.ExtraCosts = extraCosts;
        this.TotalCosts = totalCosts;
        this.Category = category;
    }

    public TuitionForm() {

    }

    public String get_id() {
        return _id;
    }

    @JsonProperty(M_MANAGER)
    public String getManager() {
        return Manager;
    }

    public void setManager(String manager) {
        Manager = manager;
    }

    @JsonProperty(M_EMPLOYEE)
    public String getEmployee() {
        return Employee;
    }

    public void setEmployee(String employee) {
        Employee = employee;
    }

    @JsonProperty(M_TRAINING)
    public String getTraining() {
        return Training;
    }

    public void setTraining(String training) {
        this.Training = training;
    }

    @JsonProperty(M_COMPANYJOINDATE)
    public Date getCompanyJoinDate() {
        return CompanyJoinDate;
    }

    public void setCompanyJoinDate(Date companyJoinDate) {
        CompanyJoinDate = companyJoinDate;
    }

    @JsonProperty(M_REASONFORCOURSE)
    public String getReasonForCourse() {
        return ReasonForCourse;
    }

    public void setReasonForCourse(String reasonForCourse) {
        ReasonForCourse = reasonForCourse;
    }

    @JsonProperty(M_STUDYCOSTS)
    public double getStudyCosts() {
        return StudyCosts;
    }

    public void setStudyCosts(double studyCosts) {
        StudyCosts = studyCosts;
    }

    @JsonProperty(M_EXAMINATIONFEES)
    public double getExaminationFees() {
        return ExaminationFees;
    }

    public void setExaminationFees(double examinationFees) {
        ExaminationFees = examinationFees;
    }

    @JsonProperty(M_TRANSPORTCOSTS)
    public double getTransportCosts() {
        return TransportCosts;
    }

    public void setTransportCosts(double transportCosts) {
        TransportCosts = transportCosts;
    }

    @JsonProperty(M_ACCOMODATIONCOSTS)
    public double getAccommodationCosts() {
        return AccommodationCosts;
    }

    public void setAccommodationCosts(double accommodationCosts) {
        AccommodationCosts = accommodationCosts;
    }

    @JsonProperty(M_EXTRACOSTS)
    public double getExtraCosts() {
        return ExtraCosts;
    }

    public void setExtraCosts(double extraCosts) {
        ExtraCosts = extraCosts;
    }

    @JsonProperty(M_TOTALCOSTS)
    public double getTotalCosts() {
        return TotalCosts;
    }

    public void setTotalCosts(double totalCosts) {
        TotalCosts = totalCosts;
    }

    @JsonProperty(M_CATEGORY)
    public TuitionCategory getCategory() {
        return Category;
    }

    public void setCategory(TuitionCategory category) {
        Category = category;
    }
}
