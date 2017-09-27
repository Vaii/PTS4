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
    private static final String M_SALT = "Salt";
    private static final String M_HASHEDPASSWORD = "HashedPassword";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    // Class fields.
    private String FirstName,LastName;
    private String Email;
    private Role Role;
    private String Company;
    private String Salt;
    private String HashedPassword;

    public User(String firstName, String lastName, String email, Role role, String company) {
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
                @JsonProperty(M_COMPANY) String company,
                @JsonProperty(M_SALT) String salt,
                @JsonProperty(M_HASHEDPASSWORD) String hashedPassword) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.Role = role;
        this.Company = company;
        this.Salt = salt;
        this.HashedPassword = hashedPassword;
    }

    public String get_id() {
        return _id;
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

    @JsonProperty(M_SALT)
    public String getSalt() {
        return Salt;
    }

    public void setSalt(String salt) {
        Salt = salt;
    }

    @JsonProperty(M_HASHEDPASSWORD)
    public String getHashedPassword() {
        return HashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        HashedPassword = hashedPassword;
    }

    /** All password related stuff is down below.
     */
    /**
     * Hashes a password and salt combination using SHA-512.
     * @param passwordToHash The password in plain text.
     * @param salt THe salt to add to the password.
     * @return The hashed combination of password and salt.
     */
    public String generatePassword(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return generatedPassword;
    }

    /**
     * Generate a salt to be used when hashing passwords.
     * @return The salt.
     */
    public String generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        String encodedSalt = Base64.encodeBase64String(salt);
        return encodedSalt;
    }

    /**
     * Checks the login of a user by comparing the given plain text password with the salt and hashed password in the database.
     * @param password The password to verify in plain text.
     * @return True if the login is successful.
     */
    public boolean checkLogin(String password) {
        String passwordToCheck = generatePassword(password, this.Salt);
        return passwordToCheck.equals(HashedPassword);
    }
}
