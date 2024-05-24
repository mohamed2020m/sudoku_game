package org.essabir;

import java.util.Random;

public class SudokuGenerator {
    private static final int GRID_SIZE = 9;
    private static final Random random = new Random();

    public static int[][] generate() {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        fillDiagonalBoxes(board);
        SudokuSolver.solve(board, moves -> {});
        removeElements(board);
        return board;
    }

    private static void fillDiagonalBoxes(int[][] board) {
        for (int i = 0; i < GRID_SIZE; i += 3) {
            fillBox(board, i, i);
        }
    }

    private static void fillBox(int[][] board, int row, int col) {
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = random.nextInt(GRID_SIZE) + 1;
                } while (!SudokuSolver.isValid(board, row, col, num));
                board[row + i][col + j] = num;
            }
        }
    }

    private static void removeElements(int[][] board) {
        int cellsToRemove = 40;
        while (cellsToRemove > 0) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                cellsToRemove--;
            }
        }
    }
}
