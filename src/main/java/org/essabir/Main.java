package org.essabir;

import javax.swing.*;

//public class Main {
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Sudoku Game");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(600, 600);
//            frame.setResizable(false);
//            frame.add(new SudokuPanel());
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//            frame.setIconImage(new ImageIcon("images/logo.png").getImage());
//        });
//    }
//}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InitialWindow initialWindow = new InitialWindow();
            initialWindow.setVisible(true);
            initialWindow.setIconImage(new ImageIcon("images/logo.png").getImage());
        });
    }
}