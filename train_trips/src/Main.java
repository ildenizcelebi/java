import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        String[] inputArray = readFile(args[0], true, true);
        String[][] tripArray = new String[inputArray.length][3];

        TripSchedule allTripsA = new TripSchedule(inputArray.length);
        TripSchedule allTripsD = new TripSchedule(inputArray.length);

        SimpleDateFormat formattedDate = new SimpleDateFormat("HH:mm");

        for (int i = 0; i < inputArray.length; i++) {
            tripArray[i] = inputArray[i].split("\t");
            allTripsA.addTrip(i, new Trip(tripArray[i][0], formattedDate.parse(tripArray[i][1]), Integer.parseInt(tripArray[i][2])));
            allTripsD.addTrip(i, new Trip(tripArray[i][0], formattedDate.parse(tripArray[i][1]), Integer.parseInt(tripArray[i][2])));
        }

        TripController tc = new TripController();
        tc.ArrivalSchedule(allTripsA);
        tc.DepartureSchedule(allTripsD);

        writeToFile(args[1], "", false, false);
        writeToFile(args[1], "Departure order:", true, true);
        for (int i = 0; i < inputArray.length; i++) {
            writeToFile(args[1], allTripsD.getTrip(i).getTripName() + " depart at " +
                    allTripsD.getTrip(i).getDepartureTime().toString().substring(10, 16) +
                    " Trip State:" + allTripsD.getTrip(i).getState() ,true, true);
        }
        writeToFile(args[1], "", true, true);
        writeToFile(args[1], "Arrival order:", true, true);
        for (int i = 0; i < inputArray.length; i++) {
            writeToFile(args[1], allTripsA.getTrip(i).getTripName() + " arrive at " +
                    allTripsA.getTrip(i).getArrivalTime().toString().substring(10, 16) +
                    " Trip State:" + allTripsA.getTrip(i).getState() ,true, true);
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