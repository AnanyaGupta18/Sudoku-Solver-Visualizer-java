import java.awt.Color;

public class SudokuSolver {
    private int[][] board;
    private static final int SIZE = 9;
    private SudokuVisualizer visualizer;

    public SudokuSolver(int[][] board, SudokuVisualizer visualizer) {
        this.board = board;
        this.visualizer = visualizer;
    }

    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isSafe(row, col, num)) {
                            board[row][col] = num;
                            visualizer.updateCell(row, col, num);
                            visualizer.setCellBackground(row, col, new Color(144, 238, 144)); 
                            try {
                                Thread.sleep(20); 
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (solve()) {
                                return true;
                            } else {
                                board[row][col] = 0;
                                visualizer.updateCell(row, col, 0);
                                visualizer.setCellBackground(row, col, new Color(255, 182, 193)); 
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSafe(int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num || 
                board[row - row % 3 + x / 3][col - col % 3 + x % 3] == num) {
                return false;
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }
}
