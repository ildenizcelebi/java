import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The SmartCamera class represents a smart camera device that can be turned on or off and records data to storage.
 * <p>
 * It extends the Super class, which contains common methods and attributes for all smart devices.
 */
public class SmartCamera extends Super {
    private double storage; //The amount of storage available on the device, in megabytes.
    private double mbPerMin; //The rate at which data is recorded to storage, in megabytes per minute.
    private LocalDateTime cameraDate; //The date and time when the camera was last switched on.

    /**
     * Constructor for creating a new SmartCamera object with a given name and data recording rate.
     *
     * @param name     The name of the camera.
     * @param mbPerMin The rate at which data is recorded to storage, in megabytes per minute.
     */
    public SmartCamera(String name, double mbPerMin) {
        for (int i = 0; i < Main.names.size(); i++) {
            if (Main.names.contains(name)) {
                control = false;
                writeToFile(Main.outputFile, "ERROR: There is already a smart device with same name!", true, true);
                break;
            }
        }
        if (control) {
            if (mbPerMin <= 0) {
                control = false;
                writeToFile(Main.outputFile, "ERROR: Megabyte value must be a positive number!", true, true);
            } else {
                Main.names.add(name);
                this.name = name;
                this.mbPerMin = mbPerMin;
                this.switching = "Off";
            }
        }
    }

    /**
     * Constructor for creating a new SmartCamera object with a given name, data recording rate, and status.
     *
     * @param name      The name of the camera.
     * @param mbPerMin  The rate at which data is recorded to storage, in megabytes per minute.
     * @param switching The initial status of the camera ("On" or "Off").
     */
    public SmartCamera(String name, double mbPerMin, String switching) {
        for (int i = 0; i < Main.names.size(); i++) {
            if (Main.names.contains(name)) {
                control = false;
                writeToFile(Main.outputFile, "ERROR: There is already a smart device with same name!", true, true);
                break;
            }
        }
        if (control) {
            if (mbPerMin <= 0) {
                control = false;
                writeToFile(Main.outputFile, "ERROR: Megabyte value must be a positive number!", true, true);
            } else {
                if (switching.equals("On") || switching.equals("Off")) {
                    this.name = name;
                    this.mbPerMin = mbPerMin;
                    this.switching = switching;
                    Main.names.add(name);
                    if (switching.equals("On")) {
                        this.setCameraDate(Main.initial.getTime());
                    }
                } else {
                    control = false;
                }
            }
        }
    }

    /**
     * Checks whether a smart camera device with the given name exists in the system.
     *
     * @param device the name of the smart camera device to check
     * @return true if the smart camera device exists, false otherwise
     */
    public static boolean isThere(String device) {
        for (SmartCamera camera : Main.smartCameras) {
            if (camera.getName().equals(device)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the smart camera device with the specified name from the list of smart cameras.
     * Also updates the device's storage based on the duration it was in use and writes the device's information to the output file.
     *
     * @param device the name of the device to be removed
     */
    public static void remove(String device) {
        for (SmartCamera camera : Main.smartCameras) {
            if (camera.getName().equals(device)) {
                writeToFile(Main.outputFile, "SUCCESS: Information about removed smart device is as follows:", true, true);
                camera.setSwitching("Off");
                Duration diff = Duration.between(camera.getCameraDate(), Main.initial.getTime());
                double diffSeconds = diff.toMillis() / 1000;
                camera.setStorage(camera.getStorage() + (camera.getMbPerMin() * (diffSeconds / 60)));
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String formattedNumber = decimalFormat.format(camera.getStorage());
                writeToFile(Main.outputFile, "Smart Camera " + camera.getName() + " is off and used " + formattedNumber +
                        " MB of storage so far (excluding current status), and its time to switch its status is null.", true, true);
                Main.smartCameras.remove(camera);
                break;
            }
        }
    }

    /**
     * Switches the status of the given smart camera to the given status (On or Off).
     * If the camera is already switched to the given status, it prints an error message.
     * If the camera is switched to Off, it calculates the used storage based on the time it was On.
     *
     * @param device the name of the smart camera to switch status
     * @param status the status to switch (On or Off)
     */
    public static void switchStatus(String device, String status) {
        for (SmartCamera camera : Main.smartCameras) {
            if (camera.getName().equals(device)) {
                if (camera.getSwitching().equals(status)) {
                    writeToFile(Main.outputFile, "ERROR: This device is already switched " +
                            status.toLowerCase() + "!", true, true);
                } else {
                    if (status.equals("On")) {
                        camera.setCameraDate(Main.initial.getTime());
                    } else if (status.equals("Off")) {
                        Duration diff = Duration.between(camera.getCameraDate(), Main.initial.getTime());
                        double diffSeconds = diff.toMillis() / 1000;
                        camera.setStorage(camera.getStorage() + (camera.getMbPerMin() * (diffSeconds / 60)));
                    }
                    camera.setSwitching(status);
                }
            }
        }
    }

    /**
     * Generates a z-report for the given smart camera device by printing its current status, used storage and switch time
     * information (if exists) to the output file.
     *
     * @param device the name of the smart camera device to generate a z-report for
     */
    public static void zReport(String device) {
        String status = "null";
        for (SmartCamera camera : Main.smartCameras) {
            if (camera.getName().equals(device)) {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String formattedNumber = decimalFormat.format(camera.getStorage());
                if (Main.switchTimesArray.contains(camera.getName())) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                    status = camera.getSwitchTime().format(formatter);
                }
                writeToFile(Main.outputFile, "Smart Camera " + camera.getName() + " is " + camera.getSwitching().toLowerCase() + " and used " + formattedNumber +
                        " MB of storage so far (excluding current status), and its time to switch its status is " + status + ".", true, true);
            }
        }
    }

    public LocalDateTime getCameraDate() {
        return cameraDate;
    }

    public void setCameraDate(LocalDateTime cameraDate) {
        this.cameraDate = cameraDate;
    }

    public double getStorage() {
        return storage;
    }

    public void setStorage(double storage) {
        this.storage = storage;
    }

    public double getMbPerMin() {
        return mbPerMin;
    }

    public void setMbPerMin(double mbPerMin) {
        this.mbPerMin = mbPerMin;
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