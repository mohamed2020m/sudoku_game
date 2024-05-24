package org.essabir;

import javax.swing.*;
import java.awt.*;

public class InitialWindow extends JFrame {
    public InitialWindow() {
        setTitle("Sudoku Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Add the image at the top center
        JPanel imagePanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon("images/sudoku.png"));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        imagePanel.setBackground(new Color(245, 245, 245));
        add(imagePanel, BorderLayout.NORTH);

        // Add Footer
        JPanel footer = new JPanel(new BorderLayout());
        JLabel developer = new JLabel("@2024 - Developed by Essabir Mohamed");
        developer.setHorizontalAlignment(SwingConstants.CENTER);
        developer.setFont(new Font("Comic Sans MS", Font.ITALIC, 14));
        developer.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        footer.add(developer, BorderLayout.CENTER);
        footer.setBackground(new Color(245, 245, 245));
        add(footer, BorderLayout.SOUTH);

        // Main panel with a light background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = 0;

        JButton emptyBoardButton = createStyledButton("Start Empty Board", new Color(59, 182, 108), new Color(48, 152, 78));
        emptyBoardButton.addActionListener(e -> startSudokuGame(false));

        JButton generateBoardButton = createStyledButton("Generate Board", new Color(59, 89, 182), new Color(46, 68, 126));
        generateBoardButton.addActionListener(e -> startSudokuGame(true));

        // Adding buttons in one row
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(emptyBoardButton);
        buttonPanel.add(generateBoardButton);

        mainPanel.add(buttonPanel, gbc);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color color, Color borderColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g.setColor(color.brighter());
                } else {
                    g.setColor(color);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };

        button.setFocusPainted(false);
        button.setFont(new Font("Verdana", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(230, 230, 250));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    private void startSudokuGame(boolean generateBoard) {
        JFrame frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
        SudokuPanel sudokuPanel = new SudokuPanel(generateBoard, frame, this);
        frame.add(sudokuPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setIconImage(new ImageIcon("images/logo.png").getImage());

        // Close the initial window
        setVisible(false);
    }
}
