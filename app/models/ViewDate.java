package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(this.date);
        return date;
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

    /**
     * Check overlap with another DateTime object.
     *
     * @param myDuration The duration in the parent training of this DateTime object.
     * @param other The other DateTime object to check overlap with.
     * @param otherDuration The Duration of the parent training of the other DateTime object.
     * @return true if there is a overlap between the 2 durations.
     */
    public boolean checkOverlap(Float myDuration, DateTime other,Float otherDuration ) {

        Calendar c = Calendar.getInstance();
        c.setTime(this.date);
        c.add(Calendar.DATE, myDuration.intValue());
        Date myEndDate = c.getTime();

        Calendar co = Calendar.getInstance();
        co.setTime(other.getDate());
        co.add(Calendar.DATE, otherDuration.intValue());
        Date otherEndDate = co.getTime();

        return other.getDate().after(this.date) && other.getDate().before(myEndDate) ||
                otherEndDate.after(this.date) && otherEndDate.before(myEndDate) ||
                this.date.after(other.getDate()) && this.date.before(otherEndDate);

    }
}
