import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * HandwrittenBook class represents a book with a type of "H" indicating that it is a handwritten book. It contains
 * fields for the book's ID, date, and borrowing status. It also has fields for the member ID, deadline, extend status,
 * and reading status.
 */

public class HandwrittenBook {
    private String type = "H";
    protected int id;
    protected LocalDate date;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected boolean isBorrowed = false;
    protected int memberId;
    protected LocalDate deadline;
    protected boolean isExtend = false;
    protected boolean isReading = false;

    /**
     * Constructor for HandwrittenBook class. It initializes the book's ID and type.
     *
     * @param id the ID of the book
     */
    public HandwrittenBook(int id) {
        this.type = type;
        this.id = id;
    }

    /**
     * Method for borrowing a book. It returns false because handwritten books cannot be borrowed.
     *
     * @param date     the date of borrowing
     * @param memberId the ID of the member who is borrowing the book
     * @return false because handwritten books cannot be borrowed
     */
    public boolean borrowBook(String date, int memberId) {
        return false;
    }

    public void returnBook(String date, int memberId, int bookId) {
        writeToFile(Main.outputFile, String.format("The book [%d] was returned by member [%d] at %s Fee: 0",
                bookId, memberId, date), true, true);
        this.isReading = false;
    }

    public void extendBook(String date, int memberId, int bookId) {
    }

    /**
     * Method for reading a book in the library. It checks if the book is available to be read and if the member is an
     * academic member. If the book is available to be read and the member is an academic member, it writes a message to
     * the output file and sets the book's reading status to true.
     *
     * @param date     the date of reading in the library
     * @param memberId the ID of the member who is reading the book in the library
     * @param bookId   the ID of the book being read in the library
     */
    public void readInLibrary(String date, int memberId, int bookId) {
        if (!isBorrowed && !isReading) {
            for (StudentMember member : Main.members) {
                if (member.id == memberId) {
                    if (member instanceof AcademicMember) {
                        writeToFile(Main.outputFile, String.format("The book [%d] was read in library by member [%d] at %s",
                                bookId, memberId, date), true, true);
                        this.date = LocalDate.parse(date, formatter);
                        this.memberId = memberId;
                        isReading = true;
                    } else {
                        writeToFile(Main.outputFile, "Students can not read handwritten books!", true, true);
                    }
                }
            }
        } else {
            writeToFile(Main.outputFile, "You can not read this book!", true, true);
        }
    }

    public static void number() { //getHistory
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int countOfHandwritten = 0;
        int countOfPrinted = 0;
        int countOfBorrowed = 0;
        int countOfReading = 0;
        for (HandwrittenBook book : Main.books) {
            if (book instanceof PrintedBook) {
                countOfPrinted++;
            } else {
                countOfHandwritten++;
            }
        }
        writeToFile(Main.outputFile, "Number of printed books: " + countOfPrinted, true, true);
        for (HandwrittenBook book : Main.books) {
            if (book instanceof PrintedBook) {
                writeToFile(Main.outputFile, String.format("Printed [id: %d]", book.id), true, true);
            }
        }
        writeToFile(Main.outputFile, "", true, true);
        writeToFile(Main.outputFile, "Number of handwritten books: " + countOfHandwritten, true, true);
        for (HandwrittenBook book : Main.books) {
            if (!(book instanceof PrintedBook)) {
                writeToFile(Main.outputFile, String.format("Handwritten [id: %d]", book.id), true, true);
            }
        }
        writeToFile(Main.outputFile, "", true, true);
        for (StudentMember member : Main.members) {
            countOfBorrowed += member.getBookLimit();
        }
        writeToFile(Main.outputFile, "Number of borrowed books: " + countOfBorrowed, true, true);
        for (HandwrittenBook book : Main.books) {
            if (book.isBorrowed) {
                String stringDate = book.date.format(dateFormatter);
                writeToFile(Main.outputFile, String.format("The book [%d] was borrowed by member [%d] at %s",
                        book.id, book.memberId, stringDate), true, true);
            }
        }
        writeToFile(Main.outputFile, "", true, true);
        for (HandwrittenBook book : Main.books) {
            if (book.isReading) {
                countOfReading++;
            }
        }
        writeToFile(Main.outputFile, "Number of books read in library: " + countOfReading, true, true);
        for (HandwrittenBook book : Main.books) {
            if (book.isReading) {
                String stringDate = book.date.format(dateFormatter);
                writeToFile(Main.outputFile, String.format("The book [%d] was read in library by member [%d] at %s",
                        book.id, book.memberId, stringDate), true, false);
            }
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