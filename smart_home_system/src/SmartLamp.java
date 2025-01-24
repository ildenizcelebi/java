import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;

public class SmartLamp extends Super {
    /**
     * This class represents a smart lamp device that extends the Super class.
     * <p>
     * It includes a kelvin variable representing the color temperature in Kelvin, and a brightness variable
     * <p>
     * representing the brightness level in percentage. It provides several constructors to create a SmartLamp object
     * <p>
     * with different parameters.
     */
    private int kelvin = 4000; //The kelvin value of the lamp. Default value is 4000.
    private int brightness = 100; //The brightness value of the lamp. Default value is 100%.

    public SmartLamp() {
    }

    /**
     * Constructs a SmartLamp object with a name parameter.
     *
     * @param name the name of the smart lamp
     */
    public SmartLamp(String name) {
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
     * Constructs a SmartLamp object with a name and switching status parameter.
     *
     * @param name      the name of the smart lamp
     * @param switching the switching status of the smart lamp ("On" or "Off")
     */
    public SmartLamp(String name, String switching) {
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
     * Constructs a SmartLamp object with a name, switching status, kelvin and brightness parameters.
     *
     * @param name       the name of the smart lamp
     * @param switching  the switching status of the smart lamp ("On" or "Off")
     * @param kelvin     the kelvin value of the smart lamp (must be in range of 2000K-6500K)
     * @param brightness the brightness value of the smart lamp (must be in range of 0%-100%)
     */
    public SmartLamp(String name, String switching, int kelvin, int brightness) {
        for (int i = 0; i < Main.names.size(); i++) {
            if (Main.names.contains(name)) {
                control = false;
                writeToFile(Main.outputFile, "ERROR: There is already a smart device with same name!", true, true);
                break;
            }
        }
        if (control) {
            if (switching.equals("On") || switching.equals("Off")) {
                if (kelvin >= 2000 && kelvin <= 6500) {
                    if (brightness >= 0 && brightness <= 100) {
                        this.brightness = brightness;
                        this.kelvin = kelvin;
                        this.name = name;
                        this.switching = switching;
                        Main.names.add(name);
                    } else {
                        writeToFile(Main.outputFile, "ERROR: Brightness must be in range of 0%-100%!", true, true);
                        control = false;
                    }
                } else {
                    writeToFile(Main.outputFile, "ERROR: Kelvin value must be in range of 2000K-6500K!", true, true);
                    control = false;
                }
            } else {
                writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                control = false;
            }
        }
    }

    public static boolean isThere(String device) {
        for (SmartLamp lamp : Main.smartLamps) {
            if (lamp.getName().equals(device)) {
                return true;
            }
        }
        return false;
    }

    public static void remove(String device) {
        for (SmartLamp lamp : Main.smartLamps) {
            if (lamp.getName().equals(device)) {
                writeToFile(Main.outputFile, "SUCCESS: Information about removed smart device is as follows:", true, true);
                lamp.setSwitching("Off");
                writeToFile(Main.outputFile, "Smart Lamp " + lamp.getName() + " is off and its kelvin value is " + lamp.getKelvin() +
                        "K with " + lamp.getBrightness() + "% brightness, and its time to switch its status is null.", true, true);
                Main.smartLamps.remove(lamp);
                break;
            }
        }
    }

    public static void switchStatus(String device, String status) {
        for (SmartLamp lamp : Main.smartLamps) {
            if (lamp.getName().equals(device)) {
                if (lamp.getSwitching().equals(status)) {
                    writeToFile(Main.outputFile, "ERROR: This device is already switched " + status.toLowerCase() + "!", true, true);
                } else {
                    lamp.setSwitching(status);
                }
            }
        }
    }

    public static void zReport(String device) {
        String status = "null";
        for (SmartLamp lamp : Main.smartLamps) {
            if (lamp.getName().equals(device)) {
                if (Main.switchTimesArray.contains(lamp.getName())) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                    status = lamp.getSwitchTime().format(formatter);
                }
                writeToFile(Main.outputFile, "Smart Lamp " + lamp.getName() + " is " + lamp.getSwitching().toLowerCase() + " and its kelvin value is " + lamp.getKelvin() +
                        "K with " + lamp.getBrightness() + "% brightness, and its time to switch its status is " + status + ".", true, true);
            }
        }
    }

    public void setKelvin(int kelvin) {
        if (kelvin >= 2000 && kelvin <= 6500) {
            this.kelvin = kelvin;
        } else {
            writeToFile(Main.outputFile, "ERROR: Kelvin value must be in range of 2000K-6500K!", true, true);
        }
    }

    public void setBrightness(int brightness) {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
        } else {
            Main.isOk = false;
            writeToFile(Main.outputFile, "ERROR: Brightness must be in range of 0%-100%!", true, true);
        }
    }

    public int getKelvin() {
        return kelvin;
    }

    public int getBrightness() {
        return brightness;
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