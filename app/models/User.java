package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class User {
    // Mongo DB identifiers.
    private static final String M_FIRSTNAME = "FirstName";
    private static final String M_LASTNAME = "LastName";
    private static final String M_EMAIL = "Email";
    private static final String M_ROLE = "Role";
    private static final String M_COMPANY = "Company";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    private String FirstName,LastName;
    private String Email;
    private Role Role;
    private String Company;

    public User(String _id, String firstName, String lastName, String email, Role role, String company) {
        this._id = _id;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.Role = role;
        this.Company = company;
    }

    @JsonCreator
    public User(@JsonProperty(M_FIRSTNAME) String firstName,
                @JsonProperty(M_LASTNAME) String lastName,
                @JsonProperty(M_EMAIL) String email,
                @JsonProperty(M_ROLE) Role role,
                @JsonProperty(M_COMPANY) String company) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.Role = role;
        this.Company = company;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
