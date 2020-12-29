package com.dandykong.timeshift;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Timestamp {
    private static final String TIMESTAMP_LINE_STRING = "(\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d)";
    private static final Pattern TIMESTAMP_LINE_PATTERN = Pattern.compile(TIMESTAMP_LINE_STRING);

    private final int hours;
    private final int minutes;
    private final int seconds;
    private final int millis;

    public Timestamp(int hours, int minutes, int seconds, int millis) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.millis = millis;
    }

    public static Timestamp fromString(String timestampString) {
        Matcher matcher = TIMESTAMP_LINE_PATTERN.matcher(timestampString);
        if (matcher.find() && matcher.groupCount() == 4) {
            return new Timestamp(Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)));
        }

        return null;
    }

    public static Timestamp fromMatcher(Matcher matcher) {
        if (matcher.groupCount() == 4) {
            return new Timestamp(Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)));
        }

        return null;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMillis() {
        return millis;
    }

    public Timestamp shift(int secondShift, int milliShift) {
        int shiftedHours = hours;
        int shiftedMinutes = minutes;
        int shiftedSeconds = seconds + secondShift;
        int shiftedMillis = millis + milliShift;

        if (shiftedMillis >= 1000) {
            shiftedMillis -= 1000;
            shiftedSeconds += 1;
        } else if (shiftedMillis < 0) {
            shiftedMillis += 1000;
            shiftedSeconds -= 1;
        }

        if (shiftedSeconds >= 60) {
            shiftedSeconds -= 60;
            shiftedMinutes += 1;
        } else if (shiftedSeconds < 0) {
            shiftedSeconds += 60;
            shiftedMinutes -= 1;
        }

        if (shiftedMinutes >= 60) {
            shiftedMinutes -= 60;
            shiftedHours += 1;
        } else if (shiftedMinutes < 0) {
            shiftedMinutes += 60;
            shiftedHours -= 1;
        }

        if (shiftedHours < 0) {
            // removed more than there was in the timestamp... that's not possible!
            return null;
        }

        return new Timestamp(shiftedHours, shiftedMinutes, shiftedSeconds, shiftedMillis);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timestamp timestamp = (Timestamp) o;
        return hours == timestamp.hours &&
                minutes == timestamp.minutes &&
                seconds == timestamp.seconds &&
                millis == timestamp.millis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes, seconds, millis);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d,%03d", hours, minutes, seconds, millis);
    }
}
