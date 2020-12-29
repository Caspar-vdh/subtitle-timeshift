package com.dandykong.timeshift;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimestampTest {

    @Test
    public void shouldCreateFromString() {
        int expectedHours = 0;
        int expectedMinutes = 0;
        int expectedSeconds = 8;
        int expectedMillis = 95;

        Timestamp timestamp = Timestamp.fromString("00:00:08,095");
        assertEquals(expectedHours, timestamp.getHours());
        assertEquals(expectedMinutes, timestamp.getMinutes());
        assertEquals(expectedSeconds, timestamp.getSeconds());
        assertEquals(expectedMillis, timestamp.getMillis());
    }

    @Test
    public void shouldCreateString() {
        final String timestampString = "00:00:08,095";
        Timestamp timestamp = Timestamp.fromString(timestampString);
        final String actual = timestamp.toString();
        assertEquals(timestampString, actual);
    }

    @Test
    public void shouldCorrectlyShift() {
        // happy flow, no currying
        Timestamp timestampIn = new Timestamp(0, 0, 1, 100);
        Timestamp timestampOut = timestampIn.shift(1, 200);
        assertEquals(new Timestamp(0, 0, 2, 300), timestampOut);

        timestampOut = timestampIn.shift(0, -50);
        assertEquals(new Timestamp(0, 0, 1, 50), timestampOut);

        // currying to seconds
        timestampOut = timestampIn.shift(1, 950);
        assertEquals(new Timestamp(0, 0, 3, 50), timestampOut);

        timestampOut = timestampIn.shift(0, -200);
        assertEquals(new Timestamp(0, 0, 0, 900), timestampOut);

        // currying to minutes
        timestampIn = new Timestamp(0, 0, 58, 800);
        timestampOut = timestampIn.shift(1, 300);
        assertEquals(new Timestamp(0, 1, 0, 100), timestampOut);

        timestampIn = new Timestamp(0, 1, 0, 200);
        timestampOut = timestampIn.shift(0, -300);
        assertEquals(new Timestamp(0, 0, 59, 900), timestampOut);

        // currying to hours
        timestampIn = new Timestamp(0, 59, 58, 200);
        timestampOut = timestampIn.shift(1, 900);
        assertEquals(new Timestamp(1, 0, 0, 100), timestampOut);

        timestampIn = new Timestamp(1, 0, 2, 200);
        timestampOut = timestampIn.shift(-2, -300);
        assertEquals(new Timestamp(0, 59, 59, 900), timestampOut);

        // edge case, shifting more than there is in the timestamp
    }
}