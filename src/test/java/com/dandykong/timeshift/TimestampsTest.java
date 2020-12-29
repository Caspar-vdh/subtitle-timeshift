package com.dandykong.timeshift;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimestampsTest {
    @Test
    public void shouldCreateFromString() {
        String timestampsString = "00:00:08,095 --> 00:00:11,098";
        Timestamps timestamps = Timestamps.fromString(timestampsString);

        int expectedHours = 0;
        int expectedMinutes = 0;
        int expectedSeconds = 8;
        int expectedMillis = 95;

        Timestamp timestamp = timestamps.from();
        assertEquals(expectedHours, timestamp.getHours());
        assertEquals(expectedMinutes, timestamp.getMinutes());
        assertEquals(expectedSeconds, timestamp.getSeconds());
        assertEquals(expectedMillis, timestamp.getMillis());

        expectedHours = 0;
        expectedMinutes = 0;
        expectedSeconds = 11;
        expectedMillis = 98;

        timestamp = timestamps.to();
        assertEquals(expectedHours, timestamp.getHours());
        assertEquals(expectedMinutes, timestamp.getMinutes());
        assertEquals(expectedSeconds, timestamp.getSeconds());
        assertEquals(expectedMillis, timestamp.getMillis());
    }

    @Test
    public void shouldCreateString() {
        String timestampsString = "00:00:08,095 --> 00:00:11,098";
        Timestamps timestamps = Timestamps.fromString(timestampsString);
        assertEquals(timestampsString, timestamps.toString());
    }

    @Test
    public void shouldShiftAndCreateString() {
        String timestampsString = "00:00:08,095 --> 00:00:11,098";
        String shiftedTimestampsString = "00:00:09,195 --> 00:00:12,198";
        Timestamps timestamps = Timestamps.fromString(timestampsString).shift(1, 100);
        assertEquals(shiftedTimestampsString, timestamps.toString());
    }
}