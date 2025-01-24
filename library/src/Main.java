import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the main library system that manages the borrowing, returning, and reading of books by members.
 */

public class Main {

    // Static variables to store the members, books, and output file path.
    static ArrayList<StudentMember> members = new ArrayList<>();
    static ArrayList<HandwrittenBook> books = new ArrayList<>();
    static String outputFile;

    /**
     * The main method of the program. It reads the input file, processes each command, and writes the output to a file.
     *
     * @param args An array of command-line arguments. The first argument should be the path to the input file, and the second argument should be the path to the output file.
     */
    public static void main(String[] args) {
        String[] inputArray = readFile(args[0], true, true);
        outputFile = args[1];
        writeToFile(Main.outputFile, "", false, false);

        int bookId = 0;
        int memberId = 0;

        // Loop through each line in the input file.
        for (int i = 0; i < inputArray.length; i++) {
            String[] line = inputArray[i].split("\t");
            // Process each command based on the first element of the line.
            switch (line[0]) {
                case "addBook": // If the command is to add a book, create a new book object and add it to the books list.
                    if (line[1].equals("H")) {
                        bookId += 1;
                        HandwrittenBook handwritten = new HandwrittenBook(bookId);
                        Main.books.add(handwritten);
                        writeToFile(Main.outputFile, String.format("Created new book: Handwritten [id: %d]", bookId), true, true);
                    } else {
                        bookId += 1;
                        PrintedBook printed = new PrintedBook(bookId);
                        Main.books.add(printed);
                        writeToFile(Main.outputFile, String.format("Created new book: Printed [id: %d]", bookId), true, true);
                    }
                    break;
                case "addMember": // If the command is to add a member, create a new member object and add it to the members list.
                    if (line[1].equals("S")) {
                        memberId += 1;
                        StudentMember student = new StudentMember(memberId);
                        Main.members.add(student);
                        writeToFile(Main.outputFile, String.format("Created new member: Student [id: %d]", memberId), true, true);
                    } else {
                        memberId += 1;
                        AcademicMember academic = new AcademicMember(memberId);
                        Main.members.add(academic);
                        writeToFile(Main.outputFile, String.format("Created new member: Academic [id: %d]", memberId), true, true);
                    }
                    break;
                case "borrowBook": // If the command is to borrow a book, find the book with the given ID and call the borrowBook method on it.
                    for (HandwrittenBook book : books) {
                        if (book.id == Integer.parseInt(line[1])) {
                            if (book.borrowBook(line[3], Integer.parseInt(line[2]))) {
                                writeToFile(Main.outputFile, String.format("The book [%s] was borrowed by member [%s] at %s",
                                        line[1], line[2], line[3]), true, true);
                            }
                        }
                    }
                    break;
                case "returnBook": // If the command is to return a book, find the book with the given ID and call the returnBook method on it.
                    for (HandwrittenBook book : books) {
                        if (book.id == Integer.parseInt(line[1])) {
                            book.returnBook(line[3], Integer.parseInt(line[2]), Integer.parseInt(line[1]));
                        }
                    }
                    break;
                case "extendBook": // If the command is to extend a book, find the book with the given ID and call the extendBook method on it.
                    for (HandwrittenBook book : books) {
                        if (book.id == Integer.parseInt(line[1])) {
                            book.extendBook(line[3], Integer.parseInt(line[2]), Integer.parseInt(line[1]));
                        }
                    }
                    break;
                case "readInLibrary": // If the command is to read a book in library, find the book with the given ID and call the readInLibrary method on it.
                    for (HandwrittenBook book : books) {
                        if (book.id == Integer.parseInt(line[1])) {
                            book.readInLibrary(line[3], Integer.parseInt(line[2]), Integer.parseInt(line[1]));
                        }
                    }
                    break;
                case "getTheHistory":
                    writeToFile(Main.outputFile, "History of library:\n", true, true);
                    StudentMember.number();
                    HandwrittenBook.number();
                    break;
            }
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