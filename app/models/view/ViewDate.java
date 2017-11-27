package models.view;

import models.storage.Location;
import models.storage.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

public class ViewDate {
    private String dateId;
    private Date date;
    private Location location;
    private User teacher;

    public ViewDate(String dateId, Date date, Location location, User teacher) {
        this.dateId = dateId;
        this.date = date;
        this.location = location;
        this.teacher = teacher;
    }

    public Date getDate() {
        return date;
    }

    /*
     * Format date to string that is uneditable.
     */
    public String getDateString() {
        DateFormat df = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return df.format(date);
    }
    /*
     * Use for edit boxes in view.
     */
    public String getEditableString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return df.format(date);
    }

    public Location getLocation() {
        return location;
    }

    public User getTeacher() {
        return teacher;
    }

    public String getDateId() {
        return dateId;
    }

}
