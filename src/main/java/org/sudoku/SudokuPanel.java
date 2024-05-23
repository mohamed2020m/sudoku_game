package org.sudoku;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuPanel extends JPanel {
    private static final int GRID_SIZE = 9;
    private Cell[][] cells;
    private JDialog loadingDialog;

    public SudokuPanel() {
        setLayout(new BorderLayout());

        // Add the image at the top center
        JPanel imagePanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon("images/sudoku.png"));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        cells = new Cell[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new Cell(row, col);
                gridPanel.add(cells[row][col]);
            }
        }

        JButton solveButton = createStyledButton("Solve", new Color(59, 182, 108), new Color(48, 152, 78));
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoadingDialog();
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Thread.sleep(2000);
                        int[][] board = getBoard();
                        SudokuSolver.solve(board, moves -> {
                            for (int[] move : moves) {
                                cells[move[0]][move[1]].animateMove(move[2]);
                            }
                        });
                        return null;
                    }

                    @Override
                    protected void done() {
                        hideLoadingDialog();
                        int[][] board = getBoard();
                        if (SudokuSolver.solve(board, moves -> {
                            for (int[] move : moves) {
                                cells[move[0]][move[1]].animateMove(move[2]);
                            }
                        })) {
                            setBoard(board);
                            //JOptionPane.showMessageDialog(SudokuPanel.this, "Sudoku solved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            CustomDialog.showDialog((Frame) SwingUtilities.getWindowAncestor(SudokuPanel.this), "Sudoku solved successfully!", "Success");
                        } else {
                            CustomDialog.showDialog((Frame) SwingUtilities.getWindowAncestor(SudokuPanel.this), "No solution exists", "Error");
                            //JOptionPane.showMessageDialog(SudokuPanel.this, "No solution exists", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
                worker.execute();
            }
        });

        JButton newGameButton = createStyledButton("New Game", new Color(59, 89, 182), new Color(46, 68, 126));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(newGameButton);
        controlPanel.add(solveButton);

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Start a new game on startup
        startNewGame();
    }

    private void startNewGame() {
        clearBoard();
        int[][] board = SudokuGenerator.generate();
        setBoard(board);
    }

    private void clearBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col].setValue(0);
            }
        }
    }

    private int[][] getBoard() {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                board[row][col] = cells[row][col].getValue();
            }
        }
        return board;
    }

    private void setBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col].setValue(board[row][col]);
            }
        }
    }

    private void showLoadingDialog() {
        loadingDialog = new JDialog((Frame) null, "Solving...", true);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel loadingLabel = new JLabel("Solving, please wait...", JLabel.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(loadingLabel, BorderLayout.CENTER);

        JLabel spinnerLabel = new JLabel(new ImageIcon("images/spinner.gif"));
        panel.add(spinnerLabel, BorderLayout.SOUTH);

        loadingDialog.add(panel);
        loadingDialog.setSize(400, 300);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        SwingUtilities.invokeLater(() -> loadingDialog.setVisible(true));
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
        }
    }

    private JButton createStyledButton(String text, Color color, Color borderColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}