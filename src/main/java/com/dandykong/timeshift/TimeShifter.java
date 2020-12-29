package com.dandykong.timeshift;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeShifter {
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
