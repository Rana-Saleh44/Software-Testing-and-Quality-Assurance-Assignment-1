package org.jfree.data.test;
import org.jfree.data.time.*;
import org.junit.Test;
import org.jfree.date.SerialDate;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import static org.junit.Assert.*;

public class QuarterClassTest {
    Quarter quarter;

    private void arrange(Integer quart, Integer year) {
        quarter = new Quarter(quart, year);
    }

    private void arrange() {
        quarter = new Quarter();
    }
    @Test //passed
    public void testQuarterDefaultCtor() {
        arrange();
        Calendar calender = Calendar.getInstance();
        int currentMonth = calender.get(Calendar.MONTH)+1;
        int expectedQuarter = SerialDate.monthCodeToQuarter(currentMonth);
        Year expectedYear = new Year(calender.get(Calendar.YEAR));
        assertEquals(expectedYear, quarter.getYear());
        assertEquals(expectedQuarter, quarter.getQuarter());
    }

    @Test  //passed
    public void testQuarterDateCtor() {
        Calendar calender = Calendar.getInstance();
        calender.set(2003,Calendar.JANUARY,1);
        Date date = calender.getTime();
        quarter = new Quarter(date);
        assertEquals(new Year(2003), quarter.getYear());
        assertEquals(1, quarter.getQuarter());
    }

    @Test  //passed
    public void testQuarterDateAndTimeZoneCtor(){
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        Date date = new Date(100,1,1);
        quarter = new Quarter(date, timeZone);
        assertEquals(new Year(2000), quarter.getYear());
        assertEquals(1,quarter.getQuarter());
    }

    @Test  //passed
    public void testQuarterIntIntCtor(){
        arrange(1,2003);
        assertEquals(new Year(2003),quarter.getYear());
        assertEquals(1,quarter.getQuarter());
    }

    @Test(expected = IllegalArgumentException.class)  //failed
    public void testQuarterIntIntCtor_QuarterLessThan1(){
        quarter = new Quarter(-1, 2003);
    }

    @Test(expected = IllegalArgumentException.class)  //failed
    public void testQuarterIntIntCtor_QuarterGreaterThan4(){
        quarter = new Quarter(5, 2003);
    }

    @Test(expected = IllegalArgumentException.class)  //passed
    public void testQuarterIntIntCtor_YearGreaterThan9999(){
        quarter = new Quarter(2, 10000);
    }

    @Test(expected = IllegalArgumentException.class)  //passed
    public void testQuarterIntIntCtor_YearLessThan1900(){
        quarter = new Quarter(2, 1899);
    }


    @Test  //passed
    public void testQuarterIntAndYearCtor(){
        quarter = new Quarter(1, new Year(2003));
        assertEquals(new Year(2003), quarter.getYear());
        assertEquals(1, quarter.getQuarter());
    }

    @Test(expected = IllegalArgumentException.class)  //faild
    public void testQuarterIntAndYearCtor_QuarterLessThan1(){
        quarter = new Quarter(-1, new Year(2003));
    }

    @Test(expected = IllegalArgumentException.class)  //faild
    public void testQuarterIntAndYearCtor_QuarterGreaterThan4(){
        quarter = new Quarter(5, new Year(2003));
    }

    @Test(expected = IllegalArgumentException.class)  //passed
    public void testQuarterIntAndYearCtor_YearLessThan1900(){
        quarter = new Quarter(5, new Year(1899));
    }


    @Test(expected = IllegalArgumentException.class)  //passed
    public void testQuarterIntAndYearCtor_YearGreaterThan9999(){
        quarter = new Quarter(5, new Year(10000));
    }

    @Test //passed
    public void testGetQuarter(){
        quarter = new Quarter(1, new Year(2003));
        assertEquals(1, quarter.getQuarter());
    }

    @Test //passed
    public void testGetYear(){
        quarter = new Quarter(1, new Year(2003));
        assertEquals(new Year(2003), quarter.getYear());
    }

    @Test  //passed
    public void testPrevious(){
        quarter = new Quarter(1, new Year(2001));
        Quarter previousQuarter = (Quarter) quarter.previous();
        assertEquals(new Year(2000),previousQuarter.getYear());
    }
    @Test  //passed
    public void testPrevious_YearIs1900AndQuarterLessThanOrEqual1(){
        quarter = new Quarter(1, new Year(1900));
        Quarter previousQuarter = (Quarter) quarter.previous();
        assertNull(previousQuarter);
    }

    @Test  //passed
    public void testPrevious_YearIs1900AndQuarterGreaterThan1(){
        quarter = new Quarter(2, new Year(1900));
        Quarter previousQuarter = (Quarter) quarter.previous();
        assertEquals(new Year(1900), previousQuarter.getYear());
        assertEquals(1, previousQuarter.getQuarter());
    }
    @Test  //passed
    public void testNext(){
        quarter = new Quarter(4, new Year(2001));
        Quarter nextQuarter = (Quarter) quarter.next();
        assertEquals(new Year(2002), nextQuarter.getYear());
        assertEquals(1, nextQuarter.getQuarter());
    }
    @Test  //passed
    public void testNext_YearIs8888AndQuarterLessThanOrEqual3(){
        quarter = new Quarter(3, new Year(8888));
        Quarter nextQuarter = (Quarter) quarter.next();
        assertEquals(new Year(8888), nextQuarter.getYear());
        assertEquals(4, nextQuarter.getQuarter());
    }
    @Test  //passed
    public void testGetSerialIndex(){
        arrange(1,2000);
        assertEquals(2000 * 4 + 1, quarter.getSerialIndex());
    }

    @Test  //passed
    public void testEquals_SameQuarterAndSameYear(){
        Quarter quarter1 = new Quarter(1, new Year(2001));
        Quarter quarter2 = new Quarter(1, new Year(2001));
        assertTrue(quarter1.equals(quarter2));
    }

    @Test  //passed
    public void testEquals_DifferentQuarterAndSameYear(){
        Quarter quarter1 = new Quarter(1, new Year(2001));
        Quarter quarter2 = new Quarter(2, new Year(2001));
        assertFalse(quarter1.equals(quarter2));
    }

    @Test  //passed
    public void testEquals_SameQuarterAndDifferentYear(){
        Quarter quarter1 = new Quarter(1, new Year(2000));
        Quarter quarter2 = new Quarter(1, new Year(2001));
        assertFalse(quarter1.equals(quarter2));
    }
    @Test  //passed
    public void testEquals_DifferentQuarterAndDifferentYear(){
        Quarter quarter1 = new Quarter(1, new Year(2001));
        Quarter quarter2 = new Quarter(2, new Year(2002));
        assertFalse(quarter1.equals(quarter2));
    }

    @Test  //passed
    public void testEquals_NonQuarterObject(){
        arrange(1, 2000);
        Object object = new Object();
        assertFalse(quarter.equals(object));
    }
    @Test  //passed
    public void testHashCode(){
        Quarter quarter1 = new Quarter(2, 2003);
        Quarter quarter2 = new Quarter(2, 2003);
        assertTrue(quarter1.equals(quarter2));
        int hash1 = quarter1.hashCode();
        int hash2 = quarter2.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test  //passed
    public void testCompareTo_NextQuarter(){
        Quarter quarter1 = new Quarter(1, new Year(2003));
        Quarter quarter2 = new Quarter(2, new Year(2003));
        assertTrue(quarter1.compareTo(quarter2) < 0);
    }

    @Test  //passed
    public void testCompareTo_PreviousQuarter(){
        Quarter quarter1 = new Quarter(1, new Year(2003));
        Quarter quarter2 = new Quarter(2, new Year(2003));
        assertTrue(quarter2.compareTo(quarter1) > 0);
    }

    @Test  //passed
    public void testCompareTo_EqualQuarter(){
        Quarter quarter1 = new Quarter(2, new Year(2003));
        Quarter quarter2 = new Quarter(2, new Year(2003));
        assertTrue(quarter1.compareTo(quarter2) == 0);
    }

    @Test //passed
    public void testToString(){
        quarter = new Quarter(1, new Year(2000));
        assertEquals("Q1/2000", quarter.toString());
    }
    @Test  //passed
    public void testGetFirstMillisecond(){
        arrange(1, 2003);
        Calendar calendar = Calendar.getInstance();
        long expectedFirstMillisecond = new Day(1,1,2003).getFirstMillisecond(calendar);
        assertEquals(expectedFirstMillisecond,quarter.getFirstMillisecond(calendar));
    }

    @Test  //passed
    public void testGetLastMillisecond(){
        arrange(1, 2003);
        Calendar calendar = Calendar.getInstance();
        long expectedLastMillisecond = new Day(31,3,2003).getLastMillisecond(calendar);
        assertEquals(expectedLastMillisecond,quarter.getLastMillisecond(calendar));
    }


    @Test  //passed
    public void testParseQuarter_DashDelimiterQuarterYear(){
        String input = "Q2-2024";
        Quarter quarter = Quarter.parseQuarter(input);
        assertEquals(2, quarter.getQuarter());
        assertEquals(2024, quarter.getYear().getYear());
    }


    @Test  //passed
    public void testParseQuarter_SpaceDelimiterQuarterYear(){
        String input = "Q2 2024";
        Quarter quarter = Quarter.parseQuarter(input);
        assertEquals(2, quarter.getQuarter());
        assertEquals(2024, quarter.getYear().getYear());
    }

    @Test  //passed
    public void testParseQuarter_ForwardSlashDelimiterQuarterYear(){
        String input = "Q2/2024";
        Quarter quarter = Quarter.parseQuarter(input);
        assertEquals(2, quarter.getQuarter());
        assertEquals(2024, quarter.getYear().getYear());
    }
    @Test  //passed
    public void testParseQuarter_CommaDelimiterQuarterYear(){
        String input = "Q2,2024";
        Quarter quarter = Quarter.parseQuarter(input);
        assertEquals(2, quarter.getQuarter());
        assertEquals(2024, quarter.getYear().getYear());
    }
    @Test  //passed
    public void testParseQuarter_SpaceDelimiterYearQuarter(){
        String input = "2024 Q2";
        Quarter quarter = Quarter.parseQuarter(input);
        assertEquals(2, quarter.getQuarter());
        assertEquals(2024, quarter.getYear().getYear());
    }


    @Test  //passed
    public void testParseQuarter_ForwardSlashDelimiterYearQuarter(){
        String input = "2024/Q2";
        Quarter quarter = Quarter.parseQuarter(input);
        assertEquals(2, quarter.getQuarter());
        assertEquals(2024, quarter.getYear().getYear());
    }


    @Test  //passed
    public void testParseQuarter_CommaDelimiterYearQuarter(){
        String input = "2024,Q2";
        Quarter quarter = Quarter.parseQuarter(input);
        assertEquals(2, quarter.getQuarter());
        assertEquals(2024, quarter.getYear().getYear());
    }

    @Test  //passed
    public void testParseQuarter_DashDelimiterYearQuarter(){
        String input = "2024-Q2";
        Quarter quarter = Quarter.parseQuarter(input);
        assertEquals(2, quarter.getQuarter());
        assertEquals(2024, quarter.getYear().getYear());
    }

    @Test(expected = TimePeriodFormatException.class)  //passed
    public void testParseQuarter_InvalidFormat(){
        quarter = Quarter.parseQuarter("2003/1Q");
    }
}
