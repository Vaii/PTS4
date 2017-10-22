package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;
import java.util.List;

public class DateTime {
    // Mongo DB identifiers.
    private static final String M_DATE = "Date";
    private static final String M_TRAINEES = "Trainees";

    // Mongo DB ID.
    @MongoObjectId
    private String _id;

    private Date date;
    private List<User> trainees;

    @JsonCreator
    public DateTime(@JsonProperty(M_DATE) Date date) {
        this.date = date;
    }

    public boolean signUp(User user) {
        return this.trainees.add(user);
    }

    public String get_id() {
        return _id;
    }

    @JsonProperty(M_DATE)
    public Date getDate() {
        return date;
    }




}
