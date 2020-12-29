package com.dandykong.timeshift.app;

import com.dandykong.timeshift.FileManager;
import com.dandykong.timeshift.TimeShifter;
import com.dandykong.timeshift.gui.Gui;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        new Application().startUI();
    }

    private Gui gui;

    private void startUI() {
        gui = new Gui(this::startTimeShift);
        SwingUtilities.invokeLater(() -> {
            //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            gui.createAndShowGui();
        });
    }

    private void startTimeShift(File selectedFile, int secondsShift, int millisShift) {
        final FileManager fileManager = new FileManager(gui::showErrorMessage);
        if (fileManager.setUp(selectedFile)) {
            try {
                TimeShifter.convert(fileManager.getReader(), fileManager.getWriter(), secondsShift, millisShift);
                fileManager.tearDown();
            } catch (IOException e) {
                fileManager.restore();
                gui.showErrorMessage(e.getMessage());
            }
        }
    }
}
