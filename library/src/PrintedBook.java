import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Represents a PrintedBook, a subclass of HandwrittenBook, that can be borrowed, returned, extended, or read in the library.
 * Inherits fields and methods from the HandwrittenBook class.
 */

public class PrintedBook extends HandwrittenBook {
    private String type = "P";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructs a PrintedBook object with the specified id.
     *
     * @param id The id of the book.
     */
    public PrintedBook(int id) {
        super(id);
    }

    @Override
    public boolean borrowBook(String date, int memberId) {
        if (!this.isBorrowed && !isReading) {
            for (StudentMember member : Main.members) {
                if (member.id == memberId) {
                    if (member.borrowBook()) {
                        this.isBorrowed = true;
                        this.date = LocalDate.parse(date, formatter);
                        this.memberId = memberId;
                        if (member instanceof AcademicMember) {
                            this.deadline = this.date.plusWeeks(2);
                        } else {
                            this.deadline = this.date.plusWeeks(1);
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        writeToFile(Main.outputFile, "You cannot borrow this book!", true, true);
        return false;
    }

    /**
     * Returns the book if it is borrowed by the specified member and the return date is valid.
     * Updates the book's status, due date, and the member's borrowing limit and penalty fee (if applicable).
     *
     * @param date     The date the book is returned.
     * @param memberId The id of the member who returns the book.
     * @param bookId   The id of the book being returned.
     */
    @Override
    public void returnBook(String date, int memberId, int bookId) {
        LocalDate returningDate = LocalDate.parse(date, formatter);
        for (StudentMember member : Main.members) {
            if (member.id == memberId) {
                if (!isReading) {
                    if (this.deadline.isAfter(returningDate)) {
                        member.setBookLimit(member.getBookLimit() - 1);
                        this.isBorrowed = false;
                        writeToFile(Main.outputFile, String.format("The book [%d] was returned by member [%d] at %s Fee: 0",
                                bookId, memberId, date), true, true);
                    } else {
                        long fee = ChronoUnit.DAYS.between(deadline, returningDate);
                        member.setBookLimit(member.getBookLimit() - 1);
                        this.isBorrowed = false;
                        writeToFile(Main.outputFile, String.format("The book [%d] was returned by member [%d] at %s Fee: %d",
                                bookId, memberId, date, fee), true, true);
                        writeToFile(Main.outputFile, "You must pay a penalty!", true, true);
                    }
                } else {
                    writeToFile(Main.outputFile, String.format("The book [%d] was returned by member [%d] at %s Fee: 0",
                            bookId, memberId, date), true, true);
                    this.isReading = false;
                }
            }
        }
    }

    /**
     * Extends the due date of the book if it is eligible for extension and the member is eligible to extend.
     * Updates the book's due date and records the extension in the output file.
     *
     * @param date     The date the extension is requested.
     * @param memberId The id of the member who requests the extension.
     * @param bookId   The id of the book being extended.
     */
    @Override
    public void extendBook(String date, int memberId, int bookId) {
        if (!isExtend) {
            for (StudentMember member : Main.members) {
                if (member.id == memberId) {
                    if (member instanceof AcademicMember) {
                        deadline = deadline.plusWeeks(2);
                    } else {
                        deadline = deadline.plusWeeks(1);
                    }
                    writeToFile(Main.outputFile, String.format("The deadline of book [%d] was extended by member [%d] at %s",
                            bookId, memberId, date), true, true);
                    writeToFile(Main.outputFile, String.format("New deadline of book [%d] is %s", bookId, deadline.format(formatter)), true, true);
                    isExtend = true;
                }
            }
        } else {
            writeToFile(Main.outputFile, "You cannot extend the deadline!", true, true);
        }
    }

    /**
     * Allows the member to read the book in the library if it is available.
     * Updates the book's status and records the reading in the output file.
     *
     * @param date     The date the reading starts.
     * @param memberId The id of the member who reads the book.
     * @param bookId   The id of the book being read.
     */
    @Override
    public void readInLibrary(String date, int memberId, int bookId) {
        if (!isBorrowed && !isReading) {
            for (StudentMember member : Main.members) {
                if (member.id == memberId) {
                    writeToFile(Main.outputFile, String.format("The book [%d] was read in library by member [%d] at %s", bookId, memberId, date), true, true);
                    this.date = LocalDate.parse(date, formatter);
                    this.memberId = memberId;
                    isReading = true;
                }
            }
        } else {
            writeToFile(Main.outputFile, "You can not read this book!", true, true);
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