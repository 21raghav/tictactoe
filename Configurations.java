import java.util.Arrays;

// Class to manage the game board configurations and interactions
public class Configurations {
    private char[][] board; // 2D array to represent the game board
    private int boardSize; // Size of the game board (both height and width)
    private int lengthToWin; // Number of consecutive symbols needed to win
    private int maxLevels; // Maximum depth of game tree exploration for AI

    // Constructor to initialize the game board and configuration settings
    public Configurations(int boardSize, int lengthToWin, int maxLevels) {
        this.boardSize = boardSize; // Set the size of the board
        this.lengthToWin = lengthToWin; // Set the winning condition length
        this.maxLevels = maxLevels; // Set the maximum exploration depth
        this.board = new char[boardSize][boardSize]; // Initialize the game board

        // Fill the game board with spaces, indicating empty positions
        for (char[] row : board) {
            Arrays.fill(row, ' ');
        }
    }

    // Method to create a new dictionary for storing game configurations
    public HashDictionary createDictionary() {
        // Initialize a new hash dictionary with a fixed size
        return new HashDictionary(7013); // Size chosen for better distribution
    }

    // Method to convert the current board configuration to a string
    private String boardToString() {
        StringBuilder sb = new StringBuilder(); // StringBuilder to construct the string
        for (char[] row : board) { // Iterate through each row of the board
            for (char cell : row) { // Iterate through each cell in the row
                sb.append(cell); // Append the cell's character to the string
            }
        }
        return sb.toString(); // Return the complete board configuration as a string
    }

    // Method to check if a board configuration has been repeated
    public int repeatedConfiguration(HashDictionary hashTable) {
        String config = boardToString(); // Convert the current board to a string
        return hashTable.get(config); // Check if this configuration exists in the hash table
    }

    // Method to add a new board configuration to the hash table
    public void addConfiguration(HashDictionary hashDictionary, int score) {
        String config = boardToString(); // Convert the current board to a string
        try {
            hashDictionary.put(new Data(config, score)); // Attempt to add the new configuration
        } catch (Exception e) { // Catch any exceptions (e.g., configuration already exists)
            System.out.println("Configuration already exists");
        }
    }

    // Method to save a player's move on the board
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol; // Place the player's symbol at the specified location
    }

    // Method to check if a specific square on the board is empty
    public boolean squareIsEmpty(int row, int col) {
        return board[row][col] == ' '; // Return true if the square is empty (' ')
    }

    // Method to check if a player has won the game
    public boolean wins(char symbol) {
        // Check all rows and columns for a winning condition
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j <= boardSize - lengthToWin; j++) {
                boolean rowWin = true, colWin = true;
                for (int k = 0; k < lengthToWin; k++) {
                    if (board[i][j + k] != symbol) rowWin = false; // Check row
                    if (board[j + k][i] != symbol) colWin = false; // Check column
                }
                if (rowWin || colWin) return true; // Winning condition found
            }
        }

        // Check diagonals for a winning condition
        for (int i = 0; i <= boardSize - lengthToWin; i++) {
            for (int j = 0; j <= boardSize - lengthToWin; j++) {
                boolean diagWin1 = true, diagWin2 = true;
                for (int k = 0; k < lengthToWin; k++) {
                    if (board[i + k][j + k] != symbol) diagWin1 = false; // Check one diagonal
                    if (board[i + k][j + lengthToWin - 1 - k] != symbol) diagWin2 = false; // Check the other diagonal
                }
                if (diagWin1 || diagWin2) return true; // Winning condition found
            }
        }

        return false; // No winning condition found
    }

    // Method to check if the game is a draw (no empty spaces and no winner)
    public boolean isDraw() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == ' ') return false; // Empty space found, not a draw
            }
        }
        return !wins('X') && !wins('O'); // Check if neither player has won
    }

    // Method to evaluate the board and return a score based on the game state
    public int evalBoard() {
        if (wins('O')) { // Check if 'O' (assumed to be the computer) has won
            return 3; // Computer wins
        } else if (wins('X')) { // Check if 'X' (assumed to be the human player) has won
            return 0; // Human wins
        } else if (isDraw()) {
            return 2; // Game is a draw
        } else {
            return 1; // Game is still undecided
        }
    }

}
