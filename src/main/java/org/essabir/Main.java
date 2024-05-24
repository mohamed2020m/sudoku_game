package org.essabir;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InitialWindow initialWindow = new InitialWindow();
            initialWindow.setVisible(true);
            initialWindow.setIconImage(new ImageIcon("images/logo.png").getImage());
        });
    }
}