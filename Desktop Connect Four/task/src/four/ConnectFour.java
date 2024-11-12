package four;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectFour extends JFrame {

    private boolean isXTurn = true;  // Switch between 'X' und 'O'
    private boolean won = false;
    private final Cell[][] cells = new Cell[6][7];


    public ConnectFour() {

        super("Connect 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 900);
        setLocationRelativeTo(null);

        // Main Layout
        setLayout(new BorderLayout());

        // Initialize playground
        JPanel gamePanel = new JPanel(new GridLayout(6, 7));
        initGameBoard(gamePanel);

        // Initialize Reset-Button
        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.addActionListener(e -> resetGame());
        resetButton.setBackground(new Color(212, 209, 34));

        // Add elements to the main layout
        add(gamePanel, BorderLayout.CENTER);
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initGameBoard(JPanel gamePanel) {

        for (int row = 6; row > 0; row--) {
            for (char col = 'A'; col < 'H'; col++) {
                Cell cell = new Cell(String.format("%c%d", col, row));
                cells[row-1][col-65] = cell;

                // Add ActionListener
                int column = (int) col -65;
                cell.addActionListener(e -> handleButtonClick(column));
                gamePanel.add(cell);
            }
        }
    }

    private void handleButtonClick(int column) {
        if (!won) {
            // Search for the last free cell at the bottom of the column
            for (int row = 0; row < 6; row++) {
                if (cells[row][column].getText().equals(" ")) {
                    // Set X or O
                    cells[row][column].setText(isXTurn ? "X" : "O");

                    if (checkWin(row, column)) {
                        markWinningCells();
                        won = true;
                        return;
                    }

                    isXTurn = !isXTurn;  // Switch player
                    break;
                }
            }
        }
    }

    // Reset Game logic
    private boolean checkWin(int row, int col) {
        String currentPlayer = cells[row][col].getText();
        return checkDirection(row, col, 0, 1, currentPlayer) ||  // Horizontal
                checkDirection(row, col, 1, 0, currentPlayer) ||  // Vertical
                checkDirection(row, col, 1, 1, currentPlayer) ||  // Diagonal down
                checkDirection(row, col, 1, -1, currentPlayer);   // Diagonal up
    }

    private List<Cell> winningCells = new ArrayList<>();

    private boolean checkDirection(int row, int col, int rowDir, int colDir, String player) {
        List<Cell> tempWinningCells = new ArrayList<>();
        tempWinningCells.add(cells[row][col]);

        tempWinningCells.addAll(findInDirection(row, col, rowDir, colDir, player));
        tempWinningCells.addAll(findInDirection(row, col, -rowDir, -colDir, player));

        if (tempWinningCells.size() >= 4) {
            winningCells = tempWinningCells;
            return true;
        }
        return false;
    }

    private List<Cell> findInDirection(int row, int col, int rowDir, int colDir, String player) {
        List<Cell> cellsInDirection = new ArrayList<>();
        int newRow = row + rowDir;
        int newCol = col + colDir;

        while (newRow >= 0 && newRow < cells.length && newCol >= 0 && newCol < cells[0].length
                && cells[newRow][newCol].getText().equals(player)) {
            cellsInDirection.add(cells[newRow][newCol]);
            newRow += rowDir;
            newCol += colDir;
        }

        return cellsInDirection;
    }

    private void markWinningCells() {
        for (Cell cell : winningCells) {
            cell.setBackground(Color.RED);  // Markiere die gewinnenden Zellen
        }
    }

    private void resetGame() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col].setText(" ");
                cells[row][col].setBackground(Cell.background);  // reset color
            }
        }
        isXTurn = true;
        winningCells.clear();  // clear winning cells
        won = false;
    }
}