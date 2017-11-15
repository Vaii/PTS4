package dal.contexts;

import models.storage.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class DateTimeMongoContextTest {

    private DateTimeMongoContext dateContext = new DateTimeMongoContext("DateTimeTest");

    @Before
    public void setup() {
        dateContext.removeAll();

        List<String> trainees = new ArrayList<>();
        trainees.add("fakeUser1");
        trainees.add("fakeUser2");
        trainees.add("fakeUser3");

        DateTime date1 = new DateTime(null, "FakeLocationID1", "fakeTeacherID1", trainees, "FakeTrainingID1", 10);
        DateTime date2 = new DateTime(null, "FakeLocationID2", "fakeTeacherID2", trainees, "FakeTrainingID2", 10);

        dateContext.addDateTime(date1);
        dateContext.addDateTime(date2);
    }

    @Test
    public void getDateTimeForUser() throws Exception {
        List<DateTime> dates = dateContext.getDateTimeForUser("fakeUser1");

        assertEquals(2, dates.size());
    }

    @Test
    public void getDateTimeForTeacher() throws Exception {
        List<DateTime> dates = dateContext.getDateTimeForTeacher("fakeTeacherID1");

        assertEquals(1, dates. size());
    }

}