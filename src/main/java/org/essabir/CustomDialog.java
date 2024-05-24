package org.essabir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomDialog extends JDialog {
    public CustomDialog(Frame parent, String message, String title) {
        super(parent, title, true);
        initComponents(message);
    }

    private void initComponents(String message) {
        // Panel to hold everything
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Message label
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(new Color(60, 60, 60));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, BorderLayout.CENTER);

        // Icon
        JLabel iconLabel = new JLabel(new ImageIcon("path/to/success/icon.png")); // Add path to your icon
        panel.add(iconLabel, BorderLayout.WEST);

        // OK button
        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBackground(new Color(59, 182, 108));
        okButton.setForeground(Color.WHITE);
        okButton.setBorder(BorderFactory.createLineBorder(new Color(48, 152, 78), 2));
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setPreferredSize(new Dimension(80, 30));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to dialog
        getContentPane().add(panel);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public static void showDialog(Frame parent, String message, String title) {
        CustomDialog dialog = new CustomDialog(parent, message, title);
        dialog.setVisible(true);
    }
}
