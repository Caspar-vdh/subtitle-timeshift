package com.dandykong.timeshift;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Timestamps {
    private static final String TIMESTAMP_LINE_STRING = "(\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d)";
    private static final Pattern TIMESTAMP_LINE_PATTERN = Pattern.compile(TIMESTAMP_LINE_STRING);

    private final Timestamp[] times = new Timestamp[2];

    public Timestamps(Timestamp from, Timestamp to) {
        times[0] = from;
        times[1] = to;
    }

    public static Timestamps fromString(String timestampString) {
        Matcher matcher = TIMESTAMP_LINE_PATTERN.matcher(timestampString);
        Timestamp to = null;
        Timestamp from = null;
        if (matcher.find()) {
            to = Timestamp.fromMatcher(matcher);
        }
        if (matcher.find()) {
            from = Timestamp.fromMatcher(matcher);
        }
        return new Timestamps(to, from);
    }

    public Timestamps shift(int secondShift, int milliShift) {
        Timestamp shiftedFrom = null;
        Timestamp shiftedTo = null;
        if (from() != null) {
            shiftedFrom = from().shift(secondShift, milliShift);
        }
        if (to() != null) {
            shiftedTo = to().shift(secondShift, milliShift);
        }
        return new Timestamps(shiftedFrom, shiftedTo);
    }

    public Timestamp from() {
        return times[0];
    }

    public Timestamp to() {
        return times[1];
    }

    @Override
    public String toString() {
        if (times[0] != null && times[1] != null) {
            return times[0] + " --> " + times[1];
        }
        return "";
    }
}
