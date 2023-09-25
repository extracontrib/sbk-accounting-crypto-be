package com.breadcrumb.kit.accounting.crypto.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateUtilsTest {

    @Test
    public void test_months_between_dates_1_year() {

        /* Saturday, September 24, 2022 1:13:10 PM */
        long from = 1664025190;

        /* Monday, September 25, 2023 1:13:10 PM */
        long to = 1695647590;

        assertEquals(12, DateUtils.monthsBetweenDates(from, to));
    }

    @Test
    public void test_months_between_dates_6_months() {

        /* Saturday, September 24, 2022 1:13:10 PM */
        long from = 1664025190;

        /* Saturday, March 25, 2023 1:18:46 PM */
        long to = 1679750326;

        assertEquals(6, DateUtils.monthsBetweenDates(from, to));
    }
}
