package com.dandykong.timeshift;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimeShifterTest {

    @Test
    public void endToEnd() throws IOException {
        final InputStreamReader in = new InputStreamReader(TimeShifterTest.class.getResourceAsStream("/test-input.txt"));
        final StringWriter stringWriter = new StringWriter();
        final BufferedReader reader = new BufferedReader(in);
        final BufferedWriter writer = new BufferedWriter(stringWriter);

        TimeShifter.convert(reader, writer, -1, 0);

        String expectedOutput = TestUtil.inputStreamToString(TimeShifterTest.class.getResourceAsStream(
                "/test-output.txt"));
        assertEquals(expectedOutput, stringWriter.toString());

        reader.close();
        writer.close();
    }
}
