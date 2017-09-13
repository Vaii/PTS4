package models;

import org.jongo.marshall.jackson.oid.MongoObjectId;

public class User {
    @MongoObjectId
    private String _id;
    private String FirstName,LastName;
    private String Email;
    private Role role;
    private String company;

    public User(String _id, String firstName, String lastName, String email, Role role, String company) {
        this._id = _id;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        this.role = role;
        this.company = company;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
