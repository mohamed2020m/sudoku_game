package org.essabir;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SudokuSolver {
    private static final int GRID_SIZE = 9;
    private JTextField[][] cells;
    private Model model;
    private Resource sudokuPuzzle;
    private String ns = "http://example.org/sudoku#";

    private void populateModel() {
        Property hasValue = model.getProperty(ns + "hasValue");

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = cells[row][col].getText();
                if (!value.isEmpty()) {
                    Resource cell = model.getResource(ns + "Cell_" + row + "_" + col);
                    cell.addProperty(hasValue, value);
                }
            }
        }
    }

    boolean solveSudoku() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (cells[row][col].getText().isEmpty()) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(row, col, String.valueOf(num))) {
                            cells[row][col].setText(String.valueOf(num));
                            cells[row][col].setBackground(Color.GREEN);
                            if (solveSudoku()) {
                                return true;
                            } else {
                                cells[row][col].setText("");
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, String value) {
        Property hasValue = model.getProperty(ns + "hasValue");

        // Check row
        for (int i = 0; i < 9; i++) {
            if (cells[row][i].getText().equals(value)) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (cells[i][col].getText().equals(value)) {
                return false;
            }
        }

        // Check 3x3 subgrid
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[startRow + i][startCol + j].getText().equals(value)) {
                    return false;
                }
            }
        }

        return true;
    }
    public static boolean solve(int[][] board, Consumer<List<int[]>> moveConsumer) {
        List<int[]> moves = new ArrayList<>();
        if (solve(board, 0, 0, moves)) {
            moveConsumer.accept(moves);
            return true;
        }
        return false;
    }

    private static boolean solve(int[][] board, int row, int col, List<int[]> moves) {
        if (row == GRID_SIZE) {
            return true;
        }

        if (board[row][col] != 0) {
            return solve(board, col == GRID_SIZE - 1 ? row + 1 : row, (col + 1) % GRID_SIZE, moves);
        }

        for (int num = 1; num <= GRID_SIZE; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                moves.add(new int[]{row, col, num});
                if (solve(board, col == GRID_SIZE - 1 ? row + 1 : row, (col + 1) % GRID_SIZE, moves)) {
                    return true;
                }
                board[row][col] = 0;
                moves.remove(moves.size() - 1);
            }
        }
        return false;
    }

    public static boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num || board[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }
}

