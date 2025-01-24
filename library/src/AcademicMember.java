import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * This class represents an Academic Member, a subclass of Student Member.
 * It has its own book limit and inherits the id from the StudentMember class.
 */
public class AcademicMember extends StudentMember {
    private String type = "A";
    private int bookLimit = 0; //4

    /**
     * Constructor method for creating an AcademicMember class.
     * It takes an id as a parameter and passes it to the superclass constructor.
     *
     * @param id the id of the academic member.
     */
    public AcademicMember(int id) {
        super(id);
    }

    @Override
    public boolean borrowBook() {
        if (bookLimit == 4) {
            writeToFile(Main.outputFile, "You have exceeded the borrowing limit!", true, true);
            return false;
        } else {
            bookLimit++;
            return true;
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