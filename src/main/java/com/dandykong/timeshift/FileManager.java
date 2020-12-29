package com.dandykong.timeshift;

import java.io.*;

public class FileManager {

    private BufferedReader reader;
    private BufferedWriter writer;
    private final ErrorListener errorListener;
    private String filePath;
    private File inputFile;

    public FileManager(ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public boolean setUp(File inputFile) {
        filePath = inputFile.getAbsolutePath();
        if (!inputFile.canWrite()) {
            if (errorListener != null) {
                errorListener.onError(String.format("No write permission for file path %s", filePath));
            }
            return false;
        }
        final File backupFile = new File(filePath + ".orig");
        inputFile.renameTo(backupFile);

        this.inputFile = backupFile;
        try {
            reader = new BufferedReader(new FileReader(this.inputFile));
        } catch (FileNotFoundException e) {
            if (errorListener != null) {
                errorListener.onError(String.format("Cannot open file %s for reading", inputFile.getAbsolutePath()));
            }
            return false;
        }

        try {
            writer = new BufferedWriter(new FileWriter(filePath));
        } catch (IOException e) {
            if (errorListener != null) {
                errorListener.onError(String.format("Cannot open file %s for writing", inputFile.getAbsolutePath()));
            }
            return false;
        }
        return true;
    }

    public void tearDown() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ignored) {
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ignored) {
        }
    }

    public void restore() {
        tearDown();
        final File outputFile = new File(filePath);
        if (outputFile.exists() && inputFile.exists()) {
            outputFile.delete();
            inputFile.renameTo(outputFile);
        }
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public interface ErrorListener {
        void onError(String errorMessage);
    }
}
