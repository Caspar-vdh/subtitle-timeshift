package com.dandykong.timeshift;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeShifter {
    private static final String TIMESTAMP_LINE_STRING = "(\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d)";
    private static final Pattern TIMESTAMP_LINE_PATTERN = Pattern.compile(TIMESTAMP_LINE_STRING);

    public static boolean isTimestampLine(String timestampLine) {
//        if (TIMESTAMP_LINE_PATTERN.)
        Matcher matcher = TIMESTAMP_LINE_PATTERN.matcher(timestampLine);
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                System.out.println(group);
            }
        }

        return "00:00:08,095 --> 00:00:11,098".equals(timestampLine);
    }

    public static void convert(BufferedReader in, BufferedWriter out, int shiftSeconds, int shiftMillis) throws IOException {
        // preconditions:
        // in not null
        // out not null
        // shiftSeconds from range [-59..59]
        // shiftMillis from range [-999..999]

        String lineIn;
        String lineOut;
        while (!((lineIn = in.readLine()) == null)) {
            Timestamps timestamps = Timestamps.fromString(lineIn);
            if (timestamps.from() != null && timestamps.to() != null) {
                lineOut = timestamps.shift(shiftSeconds, shiftMillis).toString();
            } else {
                lineOut = lineIn;
            }
            out.write(lineOut);
            out.newLine();
        }
        out.flush();
    }
}
