package org.jfree.data.test;

import org.jfree.data.time.Day;
import org.jfree.data.time.Quarter;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Year;
import org.junit.Test;

import java.util.Date;
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
    @Test
    public void testQuarterDefaultCtor() {
        arrange();
        assertEquals(2024, quarter.getYear().getYear());
        assertEquals(2, quarter.getQuarter());
    }
    //check the javadoc
    //check year 2023!!!
    @Test //passed
    public void testQuarterIntIntCtor_ValidInputs(){
        int quarterValue = 3;
        int yearValue = 2024;
        quarter = new Quarter(quarterValue, yearValue);
        assertEquals(yearValue, quarter.getYear().getYear());
        assertEquals(quarterValue, quarter.getQuarter());
    }

    @Test(expected = IllegalArgumentException.class) //failed
    public void testQuarterIntIntCtor_InvalidQuarter(){
        int invalidQuarterValue= 5;
        int yearValue = 2024;
        quarter = new Quarter(invalidQuarterValue, yearValue);
        assertEquals(yearValue, quarter.getYear().getYear());
        assertEquals(invalidQuarterValue, quarter.getQuarter());
    }

    @Test (expected = IllegalArgumentException.class)//passed ->check this one
    public void testQuarterIntIntCtor_InvalidYear(){
        int quarterValue = 2;
        int invalidYearValue = -2024;
        quarter = new Quarter(quarterValue, invalidYearValue);
        assertEquals(invalidYearValue, quarter.getYear().getYear());
        assertEquals(quarterValue, quarter.getQuarter());
    }

    @Test  //passed
    public void testQuarterIntYearCtor_ValidInputs(){
        int validQuarterValue = 3;
        Year validYear = new Year(2024);
        quarter = new Quarter(validQuarterValue, validYear);
        assertEquals(validYear, quarter.getYear());
        assertEquals(validQuarterValue, quarter.getQuarter());
    }

    @Test(expected = IllegalArgumentException.class)  //failed
    public void testQuarterIntYearCtor_InvalidQuarter(){
        int invalidQuarterValue = 5;
        Year validYear = new Year(2024);
        quarter = new Quarter(invalidQuarterValue, validYear);
        assertEquals(validYear, quarter.getYear());
        assertEquals(invalidQuarterValue, quarter.getQuarter());
    }

    @Test(expected = IllegalArgumentException.class)  //passed
    public void testQuarterIntYearCtor_InvalidYear(){
        int validQuarterValue = 3;
        Year invalidYear = new Year(-2024);
        quarter = new Quarter(validQuarterValue, invalidYear);
        assertEquals(invalidYear, quarter.getYear());
        assertEquals(validQuarterValue, quarter.getQuarter());
    }

    @Test  //passed ->check this
    public void testQuarterDateCtor_ValidDate(){
        Date validDate = new Date();
        quarter = new Quarter(validDate);
        assertNotNull(quarter);
    }

    @Test  //passed ->check this
    public void testQuarterDateTimeZoneCtor_ValidInputs(){
        Date validDate = new Date();
        TimeZone validTimeZone = TimeZone.getTimeZone("GMT"); //check EDT
        quarter = new Quarter(validDate, validTimeZone);
        assertNotNull(quarter);
    }

    @Test  //passed
    public void testGetQuarter_ValidQuarter(){
        int expectedQuarter = 3;
        quarter = new Quarter(expectedQuarter, new Year(2024));
        int actualQuarter = quarter.getQuarter();
        assertEquals(expectedQuarter, actualQuarter);
    }

    @Test  //passed
    public void testGetYear_validYear(){
        Year expectedYear = new Year(2024);
        quarter = new Quarter(3,expectedYear);
        Year actualYear = quarter.getYear();
        assertEquals(expectedYear, actualYear);
    }

  @Test   //passed
    public void testPrevious_QuarterGreaterThanOne(){
        Quarter currentQuarter = new Quarter(3, new Year(2024));
        RegularTimePeriod previousQuarter = currentQuarter.previous();
        assertNotNull(previousQuarter);
        assertEquals(2,((Quarter) previousQuarter).getQuarter());
        assertEquals(2024, ((Quarter) previousQuarter).getYear().getYear());

    }

    @Test   //passed
    public void testPrevious_QuarterEqualsOne(){
        Quarter currentQuarter = new Quarter(1, new Year(2024));
        RegularTimePeriod previousQuarter = currentQuarter.previous();
        assertNotNull(previousQuarter);
        assertEquals(4,((Quarter) previousQuarter).getQuarter());
        assertEquals(2023, ((Quarter) previousQuarter).getYear().getYear());
    }

    @Test  //failed
    public void testPrevious_YearIsNull(){
        Quarter currentQuarter = new Quarter(1, new Year(1900));
        RegularTimePeriod previousQuarter = currentQuarter.previous();
        assertNotNull(previousQuarter);
    }

    @Test  //passed
    public void testNext_QuarterLessThanFour(){
        Quarter currentQuarter = new Quarter(3, new Year(2024));
        RegularTimePeriod nextQuarter = currentQuarter.next();
        assertNotNull(nextQuarter);
        assertEquals(4, ((Quarter) nextQuarter).getQuarter());
        assertEquals(2024, ((Quarter) nextQuarter).getYear().getYear());
    }

    @Test  //failed
    public void testNext_QuarterEqualsFour(){
        Quarter currentQuarter = new Quarter(4, new Year(2024));
        RegularTimePeriod nextQuarter = currentQuarter.next();
        assertNotNull(nextQuarter);
        assertEquals(1, ((Quarter) nextQuarter).getQuarter());
        assertEquals(2024, ((Quarter) nextQuarter).getYear().getYear());
    }

    @Test  //faild ->check this
    public void testNext_YearIsNull(){
        Quarter currentQuarter = new Quarter(4, new Year(9999));
        RegularTimePeriod nextQuarter = currentQuarter.next();
        assertNotNull(nextQuarter);
        assertEquals(4, ((Quarter) nextQuarter).getQuarter());
        assertEquals(9999, ((Quarter) nextQuarter).getYear().getYear());
    }

    @Test  //failed ->check this
    public void TestGetSerialIndex(){
        Quarter quarter = new Quarter(2, new Year(2024));
        long serialIndex = quarter.getSerialIndex();
        assertEquals(8082L, serialIndex);
    }

    @Test  //passed
    public void testEquals_SameQuarter(){
        Quarter quarter1 = new Quarter(2, new Year(2024));
        Quarter quarter2 = new Quarter(2, new Year(2024));
        boolean result = quarter1.equals(quarter2);
        assertTrue(result);
    }

    @Test  //passed
    public void testEquals_DifferentQuarter(){
        Quarter quarter1 = new Quarter(2, new Year(2024));
        Quarter quarter2 = new Quarter(3, new Year(2024));
        boolean result = quarter1.equals(quarter2);
        assertFalse(result);
    }

    @Test  //passed
    public void testEquals_DifferentYear(){
        Quarter quarter1 = new Quarter(2, new Year(2023));
        Quarter quarter2 = new Quarter(2, new Year(2024));
        boolean result = quarter1.equals(quarter2);
        assertFalse(result);
    }

    @Test  //passed
    public void testEquals_NullObject(){
        Quarter quarter = new Quarter(2, new Year(2024));
        boolean result = quarter.equals(null);
        assertFalse(result);
    }

    @Test  //passed
    public void testEquals_NonQuarterObject(){
        Quarter quarter = new Quarter(2, new Year(2024));
        Object nonQuarterObject = new Object();
        boolean result = quarter.equals(nonQuarterObject);
        assertFalse(result);
    }

    @Test  //passed
    public void testHashCode_SameQuarterAndYear(){
        Quarter quarter1 = new Quarter(2, new Year(2024));
        Quarter quarter2 = new Quarter(2, new Year(2024));
        int hashCode1 = quarter1.hashCode();
        int hashCode2 = quarter2.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test //failed
    public void testHashCode_DifferentQuarter(){
        Quarter quarter1 = new Quarter(2, new Year(2024));
        Quarter quarter2 = new Quarter(3, new Year(2024));
        int hashCode1 = quarter1.hashCode();
        int hashCode2 = quarter2.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test //failed
    public void testHashCode_DifferentYear(){
        Quarter quarter1 = new Quarter(2, new Year(2023));
        Quarter quarter2 = new Quarter(2, new Year(2024));
        int hashCode1 = quarter1.hashCode();
        int hashCode2 = quarter2.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test  //passed
    public void testCompareTo_SameQuarterAndYear(){
        Quarter quarter1 = new Quarter(2, new Year(2024));
        Quarter quarter2 = new Quarter(2, new Year(2024));
        int result = quarter1.compareTo(quarter2);
        assertEquals(0, result);
    }

    @Test  //passed
    public void testCompareTo_SameYearAndDifferentQuarter(){
        Quarter quarter1 = new Quarter(2, new Year(2024));
        Quarter quarter2 = new Quarter(3, new Year(2024));
        int result = quarter1.compareTo(quarter2);
        assertTrue(result < 0);
    }

    @Test  //passed
    public void testCompareTo_DifferentYearAndSameQuarter(){
        Quarter quarter1 = new Quarter(2, new Year(2023));
        Quarter quarter2 = new Quarter(2, new Year(2024));
        int result = quarter1.compareTo(quarter2);
        assertTrue(result < 0);
    }

    @Test  //passed
    public void testCompareTo_DifferentYearAndQuarter(){
        Quarter quarter1 = new Quarter(2, new Year(2023));
        Quarter quarter2 = new Quarter(3, new Year(2024));
        int result = quarter1.compareTo(quarter2);
        assertTrue(result < 0);
    }

    @Test  //passed
    public void testCompareTo_RegularTimePeriod(){
        Quarter quarter = new Quarter(2, new Year(2024));
        RegularTimePeriod regularTimePeriod = new Day(1,1,2024);
        int result = quarter. compareTo(regularTimePeriod);
        assertEquals(0, result);
    }

    @Test  //passed
    public void testCompareTo_NonComparableObject(){
        Quarter quarter = new Quarter(2, new Year(2024));
        Object nonComparableObject = new Object();
        int result = quarter. compareTo(nonComparableObject);
        assertTrue(result > 0);
    }






}
