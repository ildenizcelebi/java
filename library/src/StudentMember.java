import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * This class represents a student member of the library system.
 */
public class StudentMember {
    private String type = "S"; // the type of the member (always "S" for student)
    protected int id; // the ID of the member
    private int bookLimit = 0; //2

    /**
     * Constructor for the StudentMember class.
     * @param id the ID of the student member
     */
    public StudentMember(int id) {
        this.type = type;
        this.id = id;
    }

    /**
     * Allows a student member to borrow a book.
     * @return true if the book can be borrowed (i.e. the member has not exceeded the borrowing limit), false otherwise
     */
    public boolean borrowBook() {
        if (bookLimit == 2) { // the member has already borrowed the maximum number of books
            writeToFile(Main.outputFile, "You have exceeded the borrowing limit!", true, true);
            return false;
        } else { // the member can borrow another book
            bookLimit++; // increment the number of books borrowed
            return true;
        }
    }
    /**
     * Counts the number of student and academic members and writes their IDs to the output file.
     */
    public static void number() { //getHistory
        int countOfStudent = 0;
        int countOfAcademic = 0;
        for (StudentMember member : Main.members) {
            if (member instanceof AcademicMember) {
                countOfAcademic++;
            } else {
                countOfStudent++;
            }
        }
        writeToFile(Main.outputFile, "Number of students: " + countOfStudent, true, true);
        for (StudentMember member : Main.members) {
            if (!(member instanceof AcademicMember)) {
                writeToFile(Main.outputFile, String.format("Student [id: %d]", member.id), true, true);
            }
        }
        writeToFile(Main.outputFile, "", true, true);
        writeToFile(Main.outputFile, "Number of academics: " + countOfAcademic, true, true);
        for (StudentMember member : Main.members) {
            if (member instanceof AcademicMember) {
                writeToFile(Main.outputFile, String.format("Academic [id: %d]", member.id), true, true);
            }
        }
        writeToFile(Main.outputFile, "", true, true);
    }

    public int getBookLimit() {
        return bookLimit;
    }

    public void setBookLimit(int bookLimit) {
        this.bookLimit = bookLimit;
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