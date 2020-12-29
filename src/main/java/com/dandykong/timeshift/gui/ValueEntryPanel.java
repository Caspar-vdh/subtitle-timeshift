package com.dandykong.timeshift.gui;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class ValueEntryPanel extends JPanel {
    private static final int MIN_VALUE_SECONDS = -59;
    private static final int MAX_VALUE_SECONDS = 59;
    private static final int MIN_VALUE_MILLIS = -999;
    private static final int MAX_VALUE_MILLIS = 999;

    private final JFormattedTextField secondsField;
    private final JFormattedTextField millisField;
    private final Listener listener;

    public ValueEntryPanel(Listener listener) {
        this.listener = listener;
        secondsField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        secondsField.setValue(1);
        secondsField.setColumns(2);
        secondsField.addPropertyChangeListener(
                new ValueChangeListener(MIN_VALUE_SECONDS, MAX_VALUE_SECONDS, "seconds"));

        millisField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        millisField.setValue(100);
        millisField.setColumns(3);
        millisField.addPropertyChangeListener(
                new ValueChangeListener(MIN_VALUE_MILLIS, MAX_VALUE_MILLIS, "millis"));

        add(new JLabel("Shift seconds:"));
        add(secondsField);
        add(new JLabel("Shift millis:"));
        add(millisField);
    }

    int getShiftSeconds() {
        return Integer.parseInt(secondsField.getText());
    }

    int getShiftMillis() {
        return Integer.parseInt(millisField.getText());
    }

    public interface Listener {
        void onError(String errorMessage);
    }

    private class ValueChangeListener implements PropertyChangeListener {
        private final int minValue;
        private final int maxValue;
        private final String valueTypeString;

        private ValueChangeListener(int minValue, int maxValue, String valueTypeString) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.valueTypeString = valueTypeString;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Object value = evt.getNewValue();
            if (value instanceof Integer || value instanceof Long) {
                int asInt = ((Number) value).intValue();
                if (asInt < minValue) {
                    if (listener != null) {
                        listener.onError(String.format("Minimum value for %s is %d, value entered: %d",
                                valueTypeString, minValue, asInt));
                    }
                    ((JFormattedTextField) evt.getSource()).setValue(evt.getOldValue());
                } else if (asInt > maxValue) {
                    if (listener != null) {
                        listener.onError(String.format("Maximum value for %s is %d, value entered: %d",
                                valueTypeString, maxValue, asInt));
                    }
                    ((JFormattedTextField) evt.getSource()).setValue(evt.getOldValue());
                }
            }
        }
    }
}
