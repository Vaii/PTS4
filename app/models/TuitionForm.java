package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

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
    private static final String M_CATEGORY = "TotalCosts";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    private User Manager, Employee;
    private Training Training;
    private Date CompanyJoinDate;
    private String ReasonForCourse;

    private double StudyCosts;
    private double ExaminationFees;
    private double TransportCosts;
    private double AccommodationCosts;
    private double ExtraCosts;
    private double TotalCosts;

    private TuitionCategory Category;

    @JsonCreator
    public TuitionForm(@JsonProperty(M_MANAGER) User manager,
                       @JsonProperty(M_EMPLOYEE) User employee,
                       @JsonProperty(M_TRAINING) Training training,
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

    public String get_id() {
        return _id;
    }

    @JsonProperty(M_MANAGER)
    public User getManager() {
        return Manager;
    }

    public void setManager(User manager) {
        Manager = manager;
    }

    @JsonProperty(M_EMPLOYEE)
    public User getEmployee() {
        return Employee;
    }

    public void setEmployee(User employee) {
        Employee = employee;
    }

    @JsonProperty(M_TRAINING)
    public Training getTraining() {
        return Training;
    }

    public void setTraining(Training training) {
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
