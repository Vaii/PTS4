package models.view;

import models.storage.Location;
import models.storage.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String getDateString() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(this.date);
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
