package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.binary.Base64;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

public class User {
    // Mongo DB identifiers.
    private static final String M_FIRSTNAME = "FirstName";
    private static final String M_LASTNAME = "LastName";
    private static final String M_EMAIL = "Email";
    private static final String M_ROLE = "Role";
    private static final String M_COMPANY = "Company";
    private static final String M_PHONENUMBER = "PhoneNumber";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    @play.data.validation.Constraints.Required
    private String FirstName, LastName;
    @play.data.validation.Constraints.Required
    private String Email;
    private Role Role;
    @play.data.validation.Constraints.Required
    private String Company;
    @play.data.validation.Constraints.Required
    private String PhoneNumber;

    public User() {

    }

    @JsonCreator
    public User(@JsonProperty(M_FIRSTNAME) String firstName,
                @JsonProperty(M_LASTNAME) String lastName,
                @JsonProperty(M_EMAIL) String email,
                @JsonProperty(M_ROLE) Role role,
                @JsonProperty(M_COMPANY) String company,
                @JsonProperty(M_PHONENUMBER) String phoneNumber) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.Role = role;
        this.Company = company;
        this.PhoneNumber = phoneNumber;
    }

    @JsonProperty(M_PHONENUMBER)
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumer) {
        PhoneNumber = phoneNumer;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _ID){
        _id = _ID;
    }

    @JsonProperty(M_FIRSTNAME)
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    @JsonProperty(M_LASTNAME)
    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    @JsonProperty(M_EMAIL)
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @JsonProperty(M_ROLE)
    public Role getRole() {
        return Role;
    }

    public void setRole(Role role) {
        this.Role = role;
    }

    @JsonProperty(M_COMPANY)
    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        this.Company = company;
    }

}
