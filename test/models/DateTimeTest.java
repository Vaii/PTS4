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
    private DateFormat formatter;

    @Before
    void setup() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yy");
        dateTime1 = new DateTime(formatter.parse("10/1/2000"), null, null , null);
    }

    // The following tests cover all possible overlap situations.

    // The following test will check overlap when the other datetime
    // starts between the start and end of the first object.
    // The following is a graphical representation of the situation.
    // Date 1 :    |**************|
    // Date 2 :         |**************|
    @Test
    public void OverlapTest1() {
    }

    // The following test will check overlap when the other datetime
    // starts before the start and ends before the end of the first object.
    // The following is a graphical representation of the situation.
    // Date 1 :         |**************|
    // Date 2 :    |**************|
    @Test
    public void OverlapTest2() {
    }

    // The following test will check overlap when the other datetime
    // starts and ends between the first object.
    // The following is a graphical representation of the situation.
    // Date 1 :    |*********************|
    // Date 2 :       |**************|
    @Test
    public void OverlapTest3() {
    }

    // The following test will check overlap when the other datetime
    // Starts end continuous longer the the start of the end before the first object
    // The following is a graphical representation of the situation.
    // Date 1 :       |**************|
    // Date 2 :    |*********************|
    @Test
    public void OverlapTest4() {
    }
}
