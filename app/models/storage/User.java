package models.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

public class User {
    // Mongo DB identifiers.
    private static final String M_FIRSTNAME = "firstName";
    private static final String M_LASTNAME = "lastName";
    private static final String M_EMAIL = "email";
    private static final String M_ROLE = "role";
    private static final String M_COMPANY = "company";
    private static final String M_PHONENUMBER = "phoneNumber";
    private static final String M_MANAGER = "manager";
    private static final String M_SKILLIDS = "skillIds";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    @play.data.validation.Constraints.Required
    private String firstName, lastName;
    @play.data.validation.Constraints.Required
    private String email;
    private Role role;
    @play.data.validation.Constraints.Required
    private String company;
    @play.data.validation.Constraints.Required
    private String phoneNumber;
    private String manager;

    private List<String> skillIds;
    
    public User() {
    }

    @JsonCreator
    public User(@JsonProperty(M_FIRSTNAME) String firstName,
                @JsonProperty(M_LASTNAME) String lastName,
                @JsonProperty(M_EMAIL) String email,
                @JsonProperty(M_ROLE) Role role,
                @JsonProperty(M_COMPANY) String company,
                @JsonProperty(M_PHONENUMBER) String phoneNumber,
                @JsonProperty(M_MANAGER) String manager,
                @JsonProperty(M_SKILLIDS) List<String> skillIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.manager = manager;
        this.skillIds = skillIds;
    }


    public User(String firstName, String lastName, String email,
                Role role, String company, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.company = company;
        this.phoneNumber = phoneNumber;
    }

    public User(String firstName, String lastName, String email,
                 String company, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty(M_PHONENUMBER)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id){
        this._id = id;
    }

    @JsonProperty(M_FIRSTNAME)
    public String getFirstName() {
        return firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty(M_LASTNAME)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty(M_EMAIL)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty(M_ROLE)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonProperty(M_COMPANY)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @JsonProperty(M_MANAGER)
    public String getManager() {
        return manager;
    }

    public String capatalizedRole(){
        String Role = this.getRole().toString().toLowerCase();
        return Role.substring(0,1).toUpperCase() + Role.substring(1);
    }

    @JsonProperty(M_SKILLIDS)
    public List<String> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(List<String> skillIds) {
        this.skillIds = skillIds;
    }


    public void setManager(String manager) {
        this.manager = manager;
    }
}
