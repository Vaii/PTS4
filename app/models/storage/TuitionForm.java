package models.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
    private static final String M_MANAGER = "manager";
    private static final String M_EMPLOYEE = "employee";
    private static final String M_TRAINING = "training";
    private static final String M_COMPANYJOINDATE = "companyJoinDate";
    private static final String M_REASONFORCOURSE = "reasonForCourse";
    private static final String M_STUDYCOSTS = "studyCosts";
    private static final String M_EXAMINATIONFEES = "examinationFees";
    private static final String M_TRANSPORTCOSTS = "transportCosts";
    private static final String M_ACCOMODATIONCOSTS = "accommodationCosts";
    private static final String M_EXTRACOSTS = "extraCosts";
    private static final String M_TOTALCOSTS = "totalCosts";
    private static final String M_CATEGORY = "category";
    private static final String M_STATUS = "status";
    private static final String M_EMPLOYEEID = "employeeID";
    private static final String M_DATETIMEID = "dateTimeID";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    // MongoDB ID's of both users.
    @play.data.validation.Constraints.Required
    private String manager, employee;
    // MongoDB ID of the training.
    @play.data.validation.Constraints.Required
    private String training;
    @play.data.validation.Constraints.Required
    private Date companyJoinDate;
    @play.data.validation.Constraints.Required
    private String reasonForCourse;
    @play.data.validation.Constraints.Required
    private double studyCosts;
    @play.data.validation.Constraints.Required
    private double examinationFees;
    @play.data.validation.Constraints.Required
    private double transportCosts;
    @play.data.validation.Constraints.Required
    private double accommodationCosts;
    @play.data.validation.Constraints.Required
    private double extraCosts;
    @play.data.validation.Constraints.Required
    private double totalCosts;
    @play.data.validation.Constraints.Required
    private TuitionCategory category;
    private Status status;
    private String employeeID;
    private String dateTimeID;

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
                       @JsonProperty(M_CATEGORY) TuitionCategory category,
                       @JsonProperty(M_STATUS) Status status,
                       @JsonProperty(M_DATETIMEID) String dateTimeID,
                       @JsonProperty(M_EMPLOYEEID) String employeeID) {
        this.manager = manager;
        this.employee = employee;
        this.training = training;
        this.companyJoinDate = companyJoinDate;
        this.reasonForCourse = reasonForCourse;
        this.studyCosts = studyCosts;
        this.examinationFees = examinationFees;
        this.transportCosts = transportCosts;
        this.accommodationCosts = accommodationCosts;
        this.extraCosts = extraCosts;
        this.totalCosts = totalCosts;
        this.category = category;
        this.status = status;
        this.dateTimeID = dateTimeID;
        this.employeeID = employeeID;
    }

    public TuitionForm() {

    }

    @JsonProperty
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    @JsonProperty
    public String getDateTimeID() {
        return dateTimeID;
    }

    public void setDateTimeID(String dateTimeID) {
        this.dateTimeID = dateTimeID;
    }

    @JsonProperty(M_STATUS)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return _id;
    }

    @JsonProperty(M_MANAGER)
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @JsonProperty(M_EMPLOYEE)
    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @JsonProperty(M_TRAINING)
    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    @JsonProperty(M_COMPANYJOINDATE)
    public Date getCompanyJoinDate() {
        return companyJoinDate;
    }

    public void setCompanyJoinDate(Date companyJoinDate) {
        this.companyJoinDate = companyJoinDate;
    }

    @JsonProperty(M_REASONFORCOURSE)
    public String getReasonForCourse() {
        return reasonForCourse;
    }

    public void setReasonForCourse(String reasonForCourse) {
        this.reasonForCourse = reasonForCourse;
    }

    @JsonProperty(M_STUDYCOSTS)
    public double getStudyCosts() {
        return studyCosts;
    }

    public void setStudyCosts(double studyCosts) {
        this.studyCosts = studyCosts;
    }

    @JsonProperty(M_EXAMINATIONFEES)
    public double getExaminationFees() {
        return examinationFees;
    }

    public void setExaminationFees(double examinationFees) {
        this.examinationFees = examinationFees;
    }

    @JsonProperty(M_TRANSPORTCOSTS)
    public double getTransportCosts() {
        return transportCosts;
    }

    public void setTransportCosts(double transportCosts) {
        this.transportCosts = transportCosts;
    }

    @JsonProperty(M_ACCOMODATIONCOSTS)
    public double getAccommodationCosts() {
        return accommodationCosts;
    }

    public void setAccommodationCosts(double accommodationCosts) {
        this.accommodationCosts = accommodationCosts;
    }

    @JsonProperty(M_EXTRACOSTS)
    public double getExtraCosts() {
        return extraCosts;
    }

    public void setExtraCosts(double extraCosts) {
        this.extraCosts = extraCosts;
    }

    @JsonProperty(M_TOTALCOSTS)
    public double getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(double totalCosts) {
        this.totalCosts = totalCosts;
    }

    @JsonProperty(M_CATEGORY)
    public TuitionCategory getCategory() {
        return category;
    }

    public void setCategory(TuitionCategory category) {
        this.category = category;
    }
}
