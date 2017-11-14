package models;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class DateTimeTest {
    private DateTime dateTime1;
    private DateTime dateTime2;
    private DateFormat formatter;

    @Before
    public void setup() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yy");
        dateTime1 = new DateTime(formatter.parse("10/1/2000"), null, null , 5);
    }

    // The following tests cover all possible overlap situations.

    // The following test will check overlap when the other datetime
    // starts between the start and end of the first object.
    // The following is a graphical representation of the situation.
    // Date 1 :    |**************|
    // Date 2 :         |**************|
    @Test
    public void OverlapTest1() throws ParseException {
        dateTime2 = new DateTime(formatter.parse("12/1/2000"), null, null , 5);

        assertTrue(dateTime1.checkOverlap(dateTime2));
    }

    // The following test will check overlap when the other datetime
    // starts before the start and ends before the end of the first object.
    // The following is a graphical representation of the situation.
    // Date 1 :         |**************|
    // Date 2 :    |**************|
    @Test
    public void OverlapTest2() throws ParseException {
        dateTime2 = new DateTime(formatter.parse("8/1/2000"), null, null , 5);

        assertTrue(dateTime1.checkOverlap(dateTime2));
    }

    // The following test will check overlap when the other datetime
    // starts and ends between the first object.
    // The following is a graphical representation of the situation.
    // Date 1 :    |*********************|
    // Date 2 :       |**************|
    @Test
    public void OverlapTest3() throws ParseException {
        dateTime2 = new DateTime(formatter.parse("12/1/2000"), null, null , 2);

        assertTrue(dateTime1.checkOverlap(dateTime2));

    }

    // The following test will check overlap when the other datetime
    // Starts end continuous longer the the start of the end before the first object
    // The following is a graphical representation of the situation.
    // Date 1 :       |**************|
    // Date 2 :    |*********************|
    @Test
    public void OverlapTest4() throws ParseException {
        dateTime2 = new DateTime(formatter.parse("8/1/2000"), null, null , 10);

        assertTrue(dateTime1.checkOverlap(dateTime2));
    }

    // The following test will check overlap when the other datetime
    // and this datetime perfectly overlap.
    // The following is a graphical representation of the situation.
    // Date 1 :       |**************|
    // Date 2 :       |**************|
    @Test
    public void OverlapTest5() throws ParseException {
        dateTime2 = new DateTime(formatter.parse("10/1/2000"), null, null , 5);

        assertTrue(dateTime1.checkOverlap(dateTime2));
    }

    // The following test will check when there is no overlap between the two.
    // The following is a graphical representation of the situation.
    // Date 1 :       |**************|
    // Date 2 :                         |**************|
    @Test
    public void OverlapTest6() throws ParseException {
        dateTime2 = new DateTime(formatter.parse("20/1/2000"), null, null , 10);

        assertFalse(dateTime1.checkOverlap(dateTime2));
    }
}
