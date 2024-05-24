package org.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SudokuSolver {
    private static final int GRID_SIZE = 9;

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

