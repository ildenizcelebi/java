import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SmartPlug extends Super {
    private int voltage = 220; //The voltage of the smart plug, in volts.
    private String plugged = "Out"; //The current state of the smart plug - either "In" or "Out".
    private double ampere = 0; //The current ampere of the smart plug.
    private double totalEnergy = 0; //The total energy consumption of the smart plug.
    private LocalDateTime plugDate; //The date and time when the smart plug was plugged in and switched on.

    /**
     * Creates a new SmartPlug object with the given name.
     *
     * @param name the name of the smart plug
     */
    public SmartPlug(String name) {
        for (int i = 0; i < Main.names.size(); i++) {
            if (Main.names.contains(name)) {
                control = false;
                writeToFile(Main.outputFile, "ERROR: There is already a smart device with same name!", true, true);
                break;
            }
        }
        if (control) {
            Main.names.add(name);
            this.name = name;
            this.switching = "Off";
        }
    }

    /**
     * Creates a new SmartPlug object with the given name and switching state.
     *
     * @param name      the name of the smart plug
     * @param switching the initial switching state of the smart plug (either "On" or "Off")
     */
    public SmartPlug(String name, String switching) {
        for (int i = 0; i < Main.names.size(); i++) {
            if (Main.names.contains(name)) {
                control = false;
                writeToFile(Main.outputFile, "ERROR: There is already a smart device with same name!", true, true);
                break;
            }
        }
        if (control) {
            if (switching.equals("On") || switching.equals("Off")) {
                this.switching = switching;
                this.name = name;
                Main.names.add(name);
            } else {
                writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                control = false;
            }
        }
    }

    /**
     * Creates a new SmartPlug object with the given name, switching state, and ampere value.
     *
     * @param name      the name of the smart plug
     * @param switching the initial switching state of the smart plug (either "On" or "Off")
     * @param ampere    the initial ampere value of the smart plug
     */
    public SmartPlug(String name, String switching, double ampere) {
        for (int i = 0; i < Main.names.size(); i++) {
            if (Main.names.contains(name)) {
                control = false;
                writeToFile(Main.outputFile, "ERROR: There is already a smart device with same name!", true, true);
                break;
            }
        }
        if (control) {
            if (switching.equals("On") || switching.equals("Off")) {
                if (ampere <= 0) {
                    control = false;
                    writeToFile(Main.outputFile, "ERROR: Ampere value must be a positive number!", true, true);
                } else {
                    this.ampere = ampere;
                    this.name = name;
                    this.switching = switching;
                    Main.names.add(name);
                    if (switching.equals("On")) {
                        setPlugDate(Main.initial.getTime());
                    }
                }
            } else {
                writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                control = false;
            }
        }
    }

    public static boolean isThere(String device) {
        for (SmartPlug plug : Main.smartPlugs) {
            if (plug.getName().equals(device)) {
                return true;
            }
        }
        return false;
    }

    public static void remove(String device) {
        for (SmartPlug plug : Main.smartPlugs) {
            if (plug.getName().equals(device)) {
                writeToFile(Main.outputFile, "SUCCESS: Information about removed smart device is as follows:", true, true);
                plug.setSwitching("Off");
                Duration diff = Duration.between(plug.getPlugDate(), Main.initial.getTime());
                double diffSeconds = diff.toMillis() / 1000;
                plug.setTotalEnergy(plug.getTotalEnergy() + (plug.voltage * plug.getAmpere() * (diffSeconds / 3600)));
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String formattedNumber = decimalFormat.format(plug.getTotalEnergy());
                writeToFile(Main.outputFile, "Smart Plug " + plug.getName() + " is off and consumed " + formattedNumber +
                        "W so far (excluding current device), and its time to switch its status is null.", true, true);
                Main.smartPlugs.remove(plug);
                break;
            }
        }
    }

    /**
     * Plugs in a smart plug with the given device name and ampere value.
     *
     * @param device the name of the smart plug
     * @param ampere the ampere value of the smart plug to be plugged in
     */
    public static void PlugIn(String device, double ampere) {
        for (SmartPlug plug : Main.smartPlugs) {
            if (plug.getName().equals(device)) {
                if (plug.getPlugged().equals("Out")) {
                    if (ampere > 0) {
                        plug.setAmpere(ampere);
                        plug.setPlugged("In");
                        if (plug.getSwitching().equals("On")) {
                            plug.setPlugDate(Main.initial.getTime());
                        }
                    } else {
                        writeToFile(Main.outputFile, "ERROR: Ampere value must be a positive number!", true, true);
                    }
                } else {
                    writeToFile(Main.outputFile, "ERROR: There is already an item plugged in to that plug!", true, true);
                }
            }
        }
    }

    /**
     * Unplugs a device from the given smart plug and updates the energy consumption accordingly
     *
     * @param device the name of the smart plug
     */
    public static void PlugOut(String device) {
        for (SmartPlug plug : Main.smartPlugs) {
            if (plug.getName().equals(device)) {
                if (plug.getPlugged().equals("Out")) {
                    writeToFile(Main.outputFile, "ERROR: This plug has no item to plug out from that plug!", true, true);
                } else {
                    if (plug.getSwitching().equals("On")) {
                        // Calculate the time difference between the plug in and plug out events and update the energy consumption
                        Duration diff = Duration.between(plug.getPlugDate(), Main.initial.getTime());
                        double diffSeconds = diff.toMillis() / 1000;
                        plug.setTotalEnergy(plug.getTotalEnergy() + (plug.voltage * plug.getAmpere() * (diffSeconds / 3600)));
                    }
                    plug.setAmpere(0);
                    plug.setPlugged("Out");
                }
            }
        }
    }

    /**
     * Changes the switch status of the specified device.
     *
     * @param device the name of the device whose switch status is to be changed
     * @param status the new switch status to be set for the device, either "On" or "Off"
     */
    public static void switchStatus(String device, String status) {
        for (SmartPlug plug : Main.smartPlugs) {
            if (plug.getName().equals(device)) {
                if (plug.getSwitching().equals(status)) {
                    writeToFile(Main.outputFile, "ERROR: This device is already switched " + status.toLowerCase() + "!", true, true);
                } else {
                    if (status.equals("On")) {
                        if (plug.getPlugged().equals("In")) {
                            plug.setPlugDate(Main.initial.getTime());
                        }
                    } else if (status.equals("Off")) {
                        if (plug.getPlugged().equals("In")) {
                            Duration diff = Duration.between(plug.getPlugDate(), Main.initial.getTime());
                            double diffSeconds = diff.toMillis() / 1000;
                            plug.setTotalEnergy(plug.getTotalEnergy() + (plug.getVoltage() * plug.getAmpere() * (diffSeconds / 3600)));
                        }
                    }
                    plug.setSwitching(status);
                }
            }
        }
    }

    public static void zReport(String device) {
        String status = "null";
        for (SmartPlug plug : Main.smartPlugs) {
            if (plug.getName().equals(device)) {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String formattedNumber = decimalFormat.format(plug.getTotalEnergy());
                if (Main.switchTimesArray.contains(plug.getName())) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                    status = plug.getSwitchTime().format(formatter);
                }
                writeToFile(Main.outputFile, "Smart Plug " + plug.getName() + " is " + plug.getSwitching().toLowerCase() + " and consumed " + formattedNumber +
                        "W so far (excluding current device), and its time to switch its status is " + status + ".", true, true);
            }
        }
    }

    public void setAmpere(double ampere) {
        this.ampere = ampere;
    }

    public double getAmpere() {
        return ampere;
    }

    public double getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(double totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public LocalDateTime getPlugDate() {
        return plugDate;
    }

    public void setPlugDate(LocalDateTime plugDate) {
        this.plugDate = plugDate;
    }

    public int getVoltage() {
        return voltage;
    }

    public String getPlugged() {
        return plugged;
    }

    public void setPlugged(String plugged) {
        this.plugged = plugged;
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