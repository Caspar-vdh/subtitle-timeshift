package com.dandykong.timeshift.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Gui extends JFrame implements ActionListener {
    private static final String ACTION_COMMAND_OPEN_FILE = "Open a File...";
    private static final String ACTION_COMMAND_SHIFT = "Shift...";
    public static final String TITLE = "Timeshifter...";

    private final LogArea logArea;
    private final ValueEntryPanel valueEntryPanel;
    private final Listener listener;
    private JButton actionButton;
    private File selectedFile;

    public Gui(Listener listener) {
        this.listener = listener;
        logArea = new LogArea();
        valueEntryPanel = new ValueEntryPanel(logArea::setUiError);
    }

    public void createAndShowGui() {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton openButton = new JButton(ACTION_COMMAND_OPEN_FILE);
        openButton.addActionListener(this);
        actionButton = new JButton(ACTION_COMMAND_SHIFT);
        actionButton.addActionListener(this);
        actionButton.setEnabled(false);

        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(actionButton);

        getContentPane().add(buttonPanel, BorderLayout.PAGE_START);
        getContentPane().add(valueEntryPanel, BorderLayout.CENTER);
        getContentPane().add(new JScrollPane(logArea), BorderLayout.PAGE_END);

        pack();
        setVisible(true);
    }

    public void showErrorMessage(String errorMessage) {
        logArea.setTimeShifterError(errorMessage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String actionCommand = e.getActionCommand();
        if (ACTION_COMMAND_OPEN_FILE.equals(actionCommand)) {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setFileFilter(new FileNameExtensionFilter("Subtitle file (*.srt)", "srt"));

            int returnValue = jfc.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = jfc.getSelectedFile();
                logArea.setSelectedFile(selectedFile.getAbsolutePath());
                actionButton.setEnabled(true);
            }
        } else if (ACTION_COMMAND_SHIFT.equals(actionCommand)) {
            if (listener != null) {
                listener.onActionTriggered(selectedFile,
                        valueEntryPanel.getShiftSeconds(),
                        valueEntryPanel.getShiftMillis());
            }
        }
    }

    public interface Listener {
        void onActionTriggered(File selectedFile, int secondsShift, int millisShift);
    }
}
