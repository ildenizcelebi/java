import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Read the game board and moves file paths from command line arguments
        String[] board = readFile(args[0], true, true);
        String[] movesArray = readFile(args[1], true, true);

        int[] indexOfWhite = new int[2];

        // Find the position of the white ball on the board
        for (int i = 0; i < board.length; i++) {
            board[i] = board[i].replace(" ", "");
            for (int j = 0; j < board[i].length(); j++) {
                if (board[i].charAt(j) == '*') {
                    indexOfWhite[0] = i;
                    indexOfWhite[1] = j;
                }
            }
        }
        // Write the initial board to output file
        writeToFile("output.txt", "", false, false);
        writeToFile("output.txt", "Game board:", true, true);
        outputBoard(board);
        writeToFile("output.txt", "", true, true);
        writeToFile("output.txt", "Your movement is:", true, true);

        // Parse and execute each move from the moves file
        movesArray = movesArray[0].split(" ");

        boolean[] control = new boolean[]{true};
        int[] score = {0};

        for (int i = 0; i < movesArray.length && control[0]; i++) {
            String s = movesArray[i];
            switch (s) {
                case "L": // Move the white ball left
                    if (indexOfWhite[1] != 0) {
                        indexOfWhite[1] = indexOfWhite[1] - 1;
                    } else {
                        indexOfWhite[1] = board[0].length() - 1;
                    }
                    swap(indexOfWhite, board, s, control, score);
                    break;
                case "R": // Move the white ball right
                    if (indexOfWhite[1] != board[0].length() - 1) {
                        indexOfWhite[1] = indexOfWhite[1] + 1;
                    } else {
                        indexOfWhite[1] = 0;
                    }
                    swap(indexOfWhite, board, s, control, score);
                    break;
                case "U": // Move the white ball up
                    if (indexOfWhite[0] != 0) {
                        indexOfWhite[0] = indexOfWhite[0] - 1;
                    } else {
                        indexOfWhite[0] = board.length - 1;
                    }
                    swap(indexOfWhite, board, s, control, score);
                    break;
                case "D": // Move the white ball down
                    if (indexOfWhite[0] != board.length - 1) {
                        indexOfWhite[0] = indexOfWhite[0] + 1;
                    } else {
                        indexOfWhite[0] = 0;
                    }
                    swap(indexOfWhite, board, s, control, score);
                    break;
            }
            writeToFile("output.txt", s, true, false); // Write the move to the output file
            writeToFile("output.txt", " ", true, false);
        }
        // Write the final board and score to the output file
        writeToFile("output.txt", "", true, true);
        writeToFile("output.txt", "", true, true);
        writeToFile("output.txt", "Your output is:", true, true);
        outputBoard(board);
        writeToFile("output.txt", "", true, true);
        if (!control[0]) writeToFile("output.txt", "Game Over!", true, true);
        writeToFile("output.txt", "Score: " + score[0], true, true);
    }

    public static void outputBoard(String[] board) { // This method outputs the game board to a file
        for (String row : board) { // Iterate through each row of the game board
            String line = ""; // Create an empty String to hold the characters of each row
            for (int ch = 0; ch < row.length(); ch++) { // Iterate through each character in the current row and add a space after each character
                line += row.charAt(ch) + " ";
            }
            line = line.trim(); // Remove trailing space from line
            writeToFile("output.txt", line, true, true); // Write the formatted row to the output file
        }
    }

    public static void swap(int[] indexOfWhite, String[] board, String s, boolean[] control, int[] score) {
        // indexOfWhite: an array with the row and column index of the white piece
        // board: the board represented as a 2D array of Strings
        // s: the direction in which to swap the white piece (L, R, U, or D)
        // control: a boolean array used to indicate if the game has ended
        // score: an int array used to keep track of the player's score
        int currentRow = 0;
        // Find the current row where the white ball is located
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length(); column++) {
                if (board[row].charAt(column) == '*') {
                    currentRow = row;
                }
            }
        }
        // Determine the appropriate action to take depending on the color in which the white ball will be displaced
        if (board[indexOfWhite[0]].charAt(indexOfWhite[1]) == 'R') {
            // Update score and move white ball to new location
            score[0] += 10;
            board[currentRow] = board[currentRow].replace("*", "X");
            board[indexOfWhite[0]] = board[indexOfWhite[0]].substring(0, indexOfWhite[1]) + '*'
                    + board[indexOfWhite[0]].substring(indexOfWhite[1] + 1);
        } else if (board[indexOfWhite[0]].charAt(indexOfWhite[1]) == 'Y') {
            // Update score and move white token to new location
            score[0] += 5;
            board[currentRow] = board[currentRow].replace("*", "X");
            board[indexOfWhite[0]] = board[indexOfWhite[0]].substring(0, indexOfWhite[1]) + '*'
                    + board[indexOfWhite[0]].substring(indexOfWhite[1] + 1);
        } else if (board[indexOfWhite[0]].charAt(indexOfWhite[1]) == 'B') {
            // Update score and move white token to new location
            score[0] -= 5;
            board[currentRow] = board[currentRow].replace("*", "X");
            board[indexOfWhite[0]] = board[indexOfWhite[0]].substring(0, indexOfWhite[1]) + '*'
                    + board[indexOfWhite[0]].substring(indexOfWhite[1] + 1);
        } else if (board[indexOfWhite[0]].charAt(indexOfWhite[1]) == 'W') { // This block of code swaps the white ball's position based on the given direction (s)
            int rowSize = board.length;
            int columnSize = board[0].length();
            switch (s) { // switch statement to determine which direction the ball is supposed to move in
                case "L": // if left
                    if (indexOfWhite[1] < columnSize - 2) { // if white ball is not on the rightmost or second-to-last column
                        indexOfWhite[1] = indexOfWhite[1] + 2; // update index of white ball to move it 2 columns to the right
                    } else if (indexOfWhite[1] == columnSize - 2) { // if white ball is on second-to-last column
                        indexOfWhite[1] = 0; // update index of white ball to move it to the leftmost column
                    } else if (indexOfWhite[1] == columnSize - 1) { // if white ball is on rightmost column
                        indexOfWhite[1] = 1; // update index of white ball to move it to the second column from the left
                    }
                    swap(indexOfWhite, board, "L", control, score);
                    break;
                case "R": // if right
                    if (indexOfWhite[1] > 1) { // if white ball is not on the leftmost or second column
                        indexOfWhite[1] = indexOfWhite[1] - 2; // update index of white ball to move it 2 columns to the left
                    } else if (indexOfWhite[1] == 1) { // if white ball is on second column
                        indexOfWhite[1] = columnSize - 1; // update index of white ball to move it to the rightmost column
                    } else if (indexOfWhite[1] == 0) { // if white ball is on leftmost column
                        indexOfWhite[1] = columnSize - 2; // update index of white ball to move it to the second-to-last column
                    }
                    swap(indexOfWhite, board, "R", control, score);
                    break;
                case "U": // if up
                    if (indexOfWhite[0] < rowSize - 2) { // if white ball is not on the bottom or second from the bottom row
                        indexOfWhite[0] = indexOfWhite[0] + 2; // update index of white ball to move it 2 rows down
                    } else if (indexOfWhite[0] == rowSize - 2) { // if white ball is on second from the bottom row
                        indexOfWhite[0] = 0; // update index of white ball to move it to the top row
                    } else if (indexOfWhite[0] == rowSize - 1) { // if white ball is on bottom row
                        indexOfWhite[0] = 1; // update index of white ball to move it to second row from the top
                    }
                    swap(indexOfWhite, board, "U", control, score);
                    break;
                case "D": // if down
                    if (indexOfWhite[0] > 1) { // if white ball is not on the top or second from the top row
                        indexOfWhite[0] = indexOfWhite[0] - 2; // update index of white ball to move it 2 rows up
                    } else if (indexOfWhite[0] == 1) { // if white ball is on second from the top row
                        indexOfWhite[0] = rowSize - 1; // update index of white ball to move it to the bottom row
                    } else if (indexOfWhite[0] == 0) { // if white ball is on top row
                        indexOfWhite[0] = rowSize - 2; // update index of white ball to move it to second row from the bottom
                    }
                    swap(indexOfWhite, board, "D", control, score);
                    break;
            }
        } else if (board[indexOfWhite[0]].charAt(indexOfWhite[1]) == 'H') {
            board[currentRow] = board[currentRow].replace("*", " ");
            control[0] = false;

        } else {
            String color = Character.toString(board[indexOfWhite[0]].charAt(indexOfWhite[1]));
            board[currentRow] = board[currentRow].replace("*", color);
            board[indexOfWhite[0]] = board[indexOfWhite[0]].substring(0, indexOfWhite[1]) + '*'
                    + board[indexOfWhite[0]].substring(indexOfWhite[1] + 1);
        }
    }

    public static String[] readFile(String path, boolean discardEmptyLines, boolean trim) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path)); //Gets the content of file to the list.
            if (discardEmptyLines) { //Removes the lines that are empty with respect to trim.
                lines.removeIf(line -> line.trim().equals(""));
            }
            if (trim) { //Trims each line.
                lines.replaceAll(String::trim);
            }
            return lines.toArray(new String[0]);
        } catch (IOException e) { //Returns null if there is no such a file.
            e.printStackTrace();
            return null;
        }
    }

    public static void writeToFile(String path, String content, boolean append, boolean newLine) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(path, append));
            ps.print(content + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) { //Flushes all the content and closes the stream if it has been successfully created.
                ps.flush();
                ps.close();
            }
        }
    }
}