package com.dandykong.timeshift.app;

import com.dandykong.timeshift.gui.Gui;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            new Gui().createAndShowGui();
        });
    }
}
