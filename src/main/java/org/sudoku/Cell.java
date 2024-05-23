package org.sudoku;

import javax.swing.*;
import java.awt.*;

public class Cell extends JTextField {
    private static final Color DEFAULT_BORDER_COLOR = Color.GRAY;
    private int row;
    private int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        setHorizontalAlignment(JTextField.CENTER);
        setFont(new Font("Arial", Font.BOLD, 20));
        setBorder(BorderFactory.createLineBorder(DEFAULT_BORDER_COLOR));
        setEditable(true);
    }

    public int getValue() {
        String text = getText();
        if (text.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(text);
    }

    public void setValue(int value) {
        setText(value == 0 ? "" : String.valueOf(value));
        setEditable(value == 0);
        setForeground(value == 0 ? Color.BLACK : Color.GRAY);
        setBorder(BorderFactory.createLineBorder(DEFAULT_BORDER_COLOR));
    }

    public void animateMove(int value) {
        setTemporaryValue(value);
        Timer timer = new Timer(50, e -> {
            setValue(value);
            ((Timer) e.getSource()).stop();
        });
        timer.start();
    }

    public void setTemporaryValue(int value) {
        setText(value == 0 ? "" : String.valueOf(value));
    }
}
