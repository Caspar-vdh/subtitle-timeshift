package com.dandykong.timeshift.gui;

import javax.swing.*;
import java.awt.*;

public class LogArea extends JTextArea {

    private String selectedFile = "No file selected";
    private String uiError = "...";
    private String timeShifterError = "...";

    public LogArea() {
        super(5, 100);
        setMargin(new Insets(5,5,5,5));
        setEditable(false);
        updateText();
    }

    public void setSelectedFile(String selectedFile) {
        this.selectedFile = selectedFile;
        updateText();
    }

    public void setUiError(String uiError) {
        this.uiError = uiError;
        updateText();
    }

    public void setTimeShifterError(String timeShifterError) {
        this.timeShifterError = timeShifterError;
        updateText();
    }

    private void updateText() {
        setText(selectedFile
                + "\n" + uiError
                + "\n" + timeShifterError);
        invalidate();
    }
}
