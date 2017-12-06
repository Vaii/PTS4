package models.view;

import models.storage.Location;
import models.storage.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewDate implements Comparable<ViewDate> {
    private String dateId;
    private Date date;
    private Location location;
    private User teacher;
    /*
     * Amount of people that have signed up for this date.
     */
    private int signUps;

    public ViewDate(String dateId, Date date, Location location, User teacher, int signUps) {
        this.dateId = dateId;
        this.date = date;
        this.location = location;
        this.teacher = teacher;
        this.signUps = signUps;
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

    public int getSignUps() {
        return signUps;
    }

    public void setSignUps(int signUps) {
        this.signUps = signUps;
    }

    @Override
    public int compareTo(ViewDate other) {
        return this.date.compareTo(other.getDate());
    }
}
