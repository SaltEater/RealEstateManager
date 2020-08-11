package com.colin.realestatemanager;

import org.junit.Test;

import static com.colin.realestatemanager.utils.Utils.convertDollarToEuro;
import static com.colin.realestatemanager.utils.Utils.getTodayDate;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {
    @Test
    public void convert_10_dollars_to_euro_isCorrect() {
        assertEquals((int) Math.round(10 * 0.812), convertDollarToEuro(10));
    }

    @Test
    public void today_date_isCorrect() {
        assertEquals("13/08/2020", getTodayDate());
    }

}