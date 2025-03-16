import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        boolean isPlaying = true;

        while (isPlaying) {
            System.out.println("Welcome to Java Tic-Tac-Toe!");
            char playerSymbol;
            boolean isValidSymbol;

            // Player symbol selection
            do {
                System.out.print("Enter your symbol (X/O): ");
                playerSymbol = scanner.next().toUpperCase().charAt(0);
                isValidSymbol = (playerSymbol == 'X' || playerSymbol == 'O');

                if (!isValidSymbol) {
                    System.out.println("Invalid selection");
                }
            } while (!isValidSymbol);

            String botSymbol = (playerSymbol == 'X') ? "O" : "X";

            // Initialize the board
            char[][] board = {
                    {' ', ' ', ' '},
                    {' ', ' ', ' '},
                    {' ', ' ', ' '}
            };

            boolean isPlayerTurn = true;
            boolean gameEnded = false;

            while (!gameEnded) {
                if (isPlayerTurn) {
                    // Player's turn
                    displayBoard(board);
                    int row = 0;
                    int column = 0;
                    do {
                        try {
                            System.out.print("Please choose the row (1/2/3): ");
                            row = scanner.nextInt() - 1; // Convert to 0-based index
                            System.out.print("Please choose the column (1/2/3): ");
                            column = scanner.nextInt() - 1; // Convert to 0-based index

                            if (row < 0 || row > 2 || column < 0 || column > 2 || board[row][column] != ' ') {
                                System.out.println("Invalid selection");
                            } else {
                                break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid selection");
                            scanner.nextLine();
                        }
                    } while (true);

                    // Update board with player's move
                    board[row][column] = playerSymbol;
                    displayBoard(board);

                    // Check if player wins
                    if (checkWin(board, playerSymbol)) {
                        System.out.println("Congratulations, you win!");
                        gameEnded = true;
                    } else if (isBoardFull(board)) {
                        System.out.println("It's a draw!");
                        gameEnded = true;
                    } else {
                        isPlayerTurn = false; // Switch to bot's turn
                    }
                } else {
                    // Bot's turn
                    System.out.print("\nBot's turn");
                    Thread.sleep(500);
                    System.out.print(".");
                    Thread.sleep(500);
                    System.out.print(".");
                    Thread.sleep(500);
                    System.out.println(".");
                    Thread.sleep(500);

                    int row, col;
                    do {
                        row = random.nextInt(3); // Random row (0, 1, or 2)
                        col = random.nextInt(3); // Random column (0, 1, or 2)
                    } while (board[row][col] != ' '); // Checks if cell is occupied

                    // Update board with bot's move
                    board[row][col] = botSymbol.charAt(0);
                    displayBoard(board);

                    // Check if bot wins
                    if (checkWin(board, botSymbol.charAt(0))) {
                        System.out.println("The bot wins! Better luck next time.");
                        gameEnded = true;
                    } else if (isBoardFull(board)) {
                        System.out.println("It's a draw!");
                        gameEnded = true;
                    } else {
                        isPlayerTurn = true; // Switch to player's turn
                    }
                }
            }

            // Ask if the player wants to play again
            System.out.print("Do you want to play again? (Y/N): ");
            String playAgain = scanner.next().toUpperCase();
            if (!playAgain.equals("Y")) {
                isPlaying = false;
            }
        }
        System.out.println("Thanks for playing!");
    }

    static void displayBoard(char[][] board) {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println(" " + board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            if (i < 2) {
                System.out.println("-----------");
            }
        }
        System.out.println();
    }

    static boolean checkWin(char[][] board, char symbol) {
        // Check horizontal wins
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true;
            }
        }

        // Check vertical wins
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == symbol && board[1][j] == symbol && board[2][j] == symbol) {
                return true;
            }
        }

        // Check diagonal wins
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true;
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
            return true;
        }

        return false;
    }

    static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // At least one empty cell
                }
            }
        }
        return true; // All cells are occupied
    }
}