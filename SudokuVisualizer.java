import javax.swing.*;
import java.awt.*;

public class SudokuVisualizer extends JFrame {
    private final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private SudokuSolver solver;
    private int[][] board;

    public SudokuVisualizer(int[][] board) {
        this.board = board;
        solver = new SudokuSolver(board, this);

        setTitle("Sudoku Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(SIZE, SIZE));
        initBoard(gridPanel);
        add(gridPanel, BorderLayout.CENTER);
        addControls();

        setVisible(true);
    }

    private void initBoard(JPanel gridPanel) {
        Font cellFont = new Font("Arial", Font.BOLD, 20);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(cellFont);

                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(new Color(220, 220, 220));
                } else {
                    cells[row][col].setBackground(Color.WHITE);
                }

                int top = (row % 3 == 0) ? 3 : 1;
                int left = (col % 3 == 0) ? 3 : 1;
                int bottom = (row == SIZE - 1) ? 3 : 1;
                int right = (col == SIZE - 1) ? 3 : 1;

                cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                gridPanel.add(cells[row][col]);
            }
        }
    }

    private void addControls() {
        JButton solveButton = new JButton("Solve");
        solveButton.setFont(new Font("Arial", Font.BOLD, 20));
        solveButton.setBackground(new Color(70, 130, 180));
        solveButton.setForeground(Color.WHITE);
        solveButton.addActionListener(e -> new Thread(this::solveSudoku).start()); 
        add(solveButton, BorderLayout.SOUTH);
    }

    private void solveSudoku() {
        if (solver.solve()) {
            updateBoard();
        } else {
            JOptionPane.showMessageDialog(this, "No solution exists!");
        }
    }

    public void updateCell(int row, int col, int value) {
        SwingUtilities.invokeLater(() -> {
            cells[row][col].setText(value == 0 ? "" : String.valueOf(value));
            cells[row][col].setBackground(value == 0 ? Color.WHITE : new Color(144, 238, 144)); 
        });
    }

    public void setCellBackground(int row, int col, Color color) {
        SwingUtilities.invokeLater(() -> cells[row][col].setBackground(color));
    }

    private void updateBoard() {
        int[][] solvedBoard = solver.getBoard();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText(String.valueOf(solvedBoard[row][col]));
                if (board[row][col] == 0) {
                    cells[row][col].setForeground(new Color(34, 139, 34)); 
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] board = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        new SudokuVisualizer(board);
    }
}
