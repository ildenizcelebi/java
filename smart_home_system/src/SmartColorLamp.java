import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;

/**
 * The SmartColorLamp class represents a smart lamp that can change color in addition to the basic features of a SmartLamp.
 */
public class SmartColorLamp extends SmartLamp {

    private String colorCode; //The hexadecimal color code of the lamp.

    /**
     * Constructs a new SmartColorLamp object with the specified name.
     *
     * @param name the name of the smart lamp
     */
    public SmartColorLamp(String name) {
        super(name);
    }

    /**
     * Constructs a new SmartColorLamp object with the specified name and switching state.
     *
     * @param name      the name of the smart lamp
     * @param switching the switching state of the smart lamp ("On" or "Off")
     */
    public SmartColorLamp(String name, String switching) {
        super(name, switching);
    }

    /**
     * Constructs a new SmartColorLamp object with the specified name, switching state, Kelvin value, and brightness level.
     *
     * @param name       the name of the smart lamp
     * @param switching  the switching state of the smart lamp ("On" or "Off")
     * @param kelvin     the Kelvin value of the smart lamp, or the hexadecimal color code if it starts with "0x"
     * @param brightness the brightness level of the smart lamp (0-100)
     */
    public SmartColorLamp(String name, String switching, String kelvin, int brightness) {
        for (int i = 0; i < Main.names.size(); i++) {
            if (Main.names.contains(name)) {
                setControl(false);
                writeToFile(Main.outputFile, "ERROR: There is already a smart device with same name!", true, true);
                break;
            }
        }
        if (isControl()) {
            if (switching.equals("On") || switching.equals("Off")) {
                if (!kelvin.contains("x")) {
                    if (Integer.parseInt(kelvin) >= 2000 && Integer.parseInt(kelvin) <= 6500) {
                        if (brightness >= 0 && brightness <= 100) {
                            setBrightness(brightness);
                            setKelvin(Integer.parseInt(kelvin));
                            setName(name);
                            setSwitching(switching);
                            Main.names.add(name);
                        } else {
                            writeToFile(Main.outputFile, "ERROR: Brightness must be in range of 0%-100%!", true, true);
                            setControl(false);
                        }
                    } else {
                        writeToFile(Main.outputFile, "ERROR: Kelvin value must be in range of 2000K-6500K!", true, true);
                        setControl(false);
                    }
                } else {
                    try {
                        if (Integer.parseInt(kelvin.substring(2), 16) >= 0x0 && Integer.parseInt(kelvin.substring(2), 16) <= 0xFFFFFF) {
                            setBrightness(brightness);
                            setKelvin(0);
                            this.colorCode = kelvin;
                            setName(name);
                            setSwitching(switching);
                            Main.names.add(name);
                        } else {
                            setControl(false);
                            writeToFile(Main.outputFile, "ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true, true);
                        }
                    } catch (NumberFormatException e) {
                        writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                        setControl(false);
                    }
                }
            } else {
                writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                setControl(false);
            }
        }
    }

    public static boolean isThere(String device) {
        for (SmartColorLamp clamp : Main.smartColorLamps) {
            if (clamp.getName().equals(device)) {
                return true;
            }
        }
        return false;
    }

    public static void remove(String device) {
        for (SmartColorLamp clamp : Main.smartColorLamps) {
            if (clamp.getName().equals(device)) {
                writeToFile(Main.outputFile, "SUCCESS: Information about removed smart device is as follows:", true, true);
                clamp.setSwitching("Off");
                if (clamp.getColorCode() != null) {
                    writeToFile(Main.outputFile, "Smart Color Lamp " + clamp.getName() + " is off and its color value is " + clamp.getColorCode() + " with " +
                            clamp.getBrightness() + "% brightness, and its time to switch its status is null.", true, true);
                } else {
                    writeToFile(Main.outputFile, "Smart Color Lamp " + clamp.getName() + " is off and its color value is " + clamp.getKelvin() + "K with " +
                            clamp.getBrightness() + "% brightness, and its time to switch its status is null.", true, true);
                }
                Main.smartColorLamps.remove(clamp);
                break;
            }
        }
    }

    public static void switchStatus(String device, String status) {
        for (SmartColorLamp cLamp : Main.smartColorLamps) {
            if (cLamp.getName().equals(device)) {
                if (cLamp.getSwitching().equals(status)) {
                    writeToFile(Main.outputFile, "ERROR: This device is already switched " + status.toLowerCase() + "!", true, true);
                } else {
                    cLamp.setSwitching(status);
                }
            }
        }
    }

    public static void zReport(String device) {
        String status = "null";
        for (SmartColorLamp clamp : Main.smartColorLamps) {
            if (clamp.getName().equals(device)) {
                if (Main.switchTimesArray.contains(clamp.getName())) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                    status = clamp.getSwitchTime().format(formatter);
                }
                if (clamp.getColorCode() != null) {
                    writeToFile(Main.outputFile, "Smart Color Lamp " + clamp.getName() + " is " + clamp.getSwitching().toLowerCase() + " and its color value is " + clamp.getColorCode() + " with " +
                            clamp.getBrightness() + "% brightness, and its time to switch its status is " + status + ".", true, true);
                } else {
                    writeToFile(Main.outputFile, "Smart Color Lamp " + clamp.getName() + " is " + clamp.getSwitching().toLowerCase() + " and its color value is " + clamp.getKelvin() + "K with " +
                            clamp.getBrightness() + "% brightness, and its time to switch its status is " + status + ".", true, true);
                }
            }
        }
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        try {
            if (Integer.parseInt(colorCode.substring(2), 16) >= 0x0 && Integer.parseInt(colorCode.substring(2), 16) <= 0xFFFFFF) {
                this.colorCode = colorCode;
            } else {
                Main.isOk = false;
                writeToFile(Main.outputFile, "ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true, true);
            }
        } catch (NumberFormatException e) {
            Main.isOk = false;
            writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
        } catch (NullPointerException e) {
            Main.isOk = false;
            this.colorCode = null;
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