package models.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class User {
    // Mongo DB identifiers.
    private static final String M_FIRSTNAME = "firstName";
    private static final String M_LASTNAME = "lastName";
    private static final String M_EMAIL = "email";
    private static final String M_ROLE = "role";
    private static final String M_COMPANY = "company";
    private static final String M_PHONENUMBER = "phoneNumber";

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
    
    public User() {
    }

    @JsonCreator
    public User(@JsonProperty(M_FIRSTNAME) String firstName,
                @JsonProperty(M_LASTNAME) String lastName,
                @JsonProperty(M_EMAIL) String email,
                @JsonProperty(M_ROLE) Role role,
                @JsonProperty(M_COMPANY) String company,
                @JsonProperty(M_PHONENUMBER) String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
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
        _id = id;
    }

    @JsonProperty(M_FIRSTNAME)
    public String getFirstName() {
        return firstName;
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

}
