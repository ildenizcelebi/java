import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main class for running the Smart Home Management System.
 * <p>
 * It contains an ArrayList for each type of smart device, as well as an ArrayList for device names, switch times,
 * <p>
 * and an initial Time object. It also stores the output file name and a boolean flag for error handling.
 * <p>
 * The main method reads input commands from a file and performs the appropriate actions using the Smart Home
 * <p>
 * Management System classes.
 */
public class Main {
    static ArrayList<SmartPlug> smartPlugs = new ArrayList<>();
    static ArrayList<SmartCamera> smartCameras = new ArrayList<>();
    static ArrayList<SmartLamp> smartLamps = new ArrayList<>();
    static ArrayList<SmartColorLamp> smartColorLamps = new ArrayList<>();
    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<String> switchTimesArray = new ArrayList<>();
    static Time initial = new Time();
    static boolean isOk = true;
    static String outputFile;

    public static void main(String[] args) throws ParseException {
        String[] inputArray = readFile(args[0], true, true);
        outputFile = args[1];
        writeToFile(Main.outputFile, "", false, false);

        for (int i = 0; i < inputArray.length; i++) {
            String[] line = inputArray[i].split("\\s");
            if (i == 0 && (!line[0].equals("SetInitialTime") || line.length == 1)) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[0], true, true);
                writeToFile(Main.outputFile, "ERROR: First command must be set initial time! Program is going to terminate!", true, true);
                return;
            } else if (i == 0 && line[0].equals("SetInitialTime")) {
                try {
                    initial.setInitialTime(line[1]);
                    writeToFile(Main.outputFile, "COMMAND: " + inputArray[0], true, true);
                    writeToFile(Main.outputFile, "SUCCESS: Time has been set to " + line[1] + "!", true, true);
                } catch (DateTimeException e) {
                    writeToFile(Main.outputFile, "COMMAND: " + inputArray[0], true, true);
                    writeToFile(Main.outputFile, "ERROR: Format of the initial date is wrong! Program is going to terminate!", true, true);
                    return;
                }
            } else if (line[0].equals("SetTime")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                try {
                    initial.setTime(line[1]);
                } catch (DateTimeException e) {
                    writeToFile(Main.outputFile, "ERROR: Time format is not correct!", true, true);
                }
            } else if (line[0].equals("SkipMinutes")) {
                try {
                    if (line.length != 2) {
                        writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                        writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                    } else if (Integer.parseInt(line[1]) > 0) {
                        initial.skipMinutes(Integer.parseInt(line[1]));
                        writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                    } else if (Integer.parseInt(line[1]) < 0) {
                        writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                        writeToFile(Main.outputFile, "ERROR: Time cannot be reversed!", true, true);
                    } else if (Integer.parseInt(line[1]) == 0) {
                        writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                        writeToFile(Main.outputFile, "ERROR: There is nothing to skip!", true, true);
                    }
                } catch (NumberFormatException e) {
                    writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                    writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                }

            } else if (line[0].equals("Nop")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                initial.nop();


            } else if (line[0].equals("Add")) {
                switch (line[1]) {
                    case "SmartPlug":
                        writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                        if (line.length == 3) {
                            SmartPlug smartPlug = new SmartPlug(line[2]);
                            if (smartPlug.isControl()) {
                                Main.smartPlugs.add(smartPlug);
                            }
                        } else if (line.length == 4) {
                            SmartPlug smartPlug = new SmartPlug(line[2], line[3]);
                            if (smartPlug.isControl()) {
                                Main.smartPlugs.add(smartPlug);
                            }
                        } else if (line.length == 5) {
                            SmartPlug smartPlug = new SmartPlug(line[2], line[3], Double.parseDouble(line[4]));
                            if (smartPlug.isControl()) {
                                Main.smartPlugs.add(smartPlug);
                            }
                        } else {
                            writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                        }
                        break;
                    case "SmartCamera":
                        writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                        if (line.length == 4) {
                            SmartCamera smartCamera = new SmartCamera(line[2], Double.parseDouble(line[3]));
                            if (smartCamera.isControl()) {
                                Main.smartCameras.add(smartCamera);
                            }
                        } else if (line.length == 5) {
                            SmartCamera smartCamera = new SmartCamera(line[2], Double.parseDouble(line[3]), line[4]);
                            if (smartCamera.isControl()) {
                                Main.smartCameras.add(smartCamera);
                            }
                        } else {
                            writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                        }
                        break;
                    case "SmartLamp":
                        writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                        if (line.length == 3) {
                            SmartLamp smartLamp = new SmartLamp(line[2]);
                            if (smartLamp.isControl()) {
                                Main.smartLamps.add(smartLamp);
                            }
                        } else if (line.length == 4) {
                            SmartLamp smartLamp = new SmartLamp(line[2], line[3]);
                            if (smartLamp.isControl()) {
                                Main.smartLamps.add(smartLamp);
                            }
                        } else if (line.length == 6) {
                            SmartLamp smartLamp = new SmartLamp(line[2], line[3], Integer.parseInt(line[4]), Integer.parseInt(line[5]));
                            if (smartLamp.isControl()) {
                                Main.smartLamps.add(smartLamp);
                            }
                        } else {
                            writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                        }
                        break;
                    case "SmartColorLamp":
                        writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                        if (line.length == 3) {
                            SmartColorLamp smartColorLamp = new SmartColorLamp(line[2]);
                            if (smartColorLamp.isControl()) {
                                Main.smartColorLamps.add(smartColorLamp);
                            }
                        } else if (line.length == 4) {
                            SmartColorLamp smartColorLamp = new SmartColorLamp(line[2], line[3]);
                            if (smartColorLamp.isControl()) {
                                Main.smartColorLamps.add(smartColorLamp);
                            }
                        } else if (line.length == 6) {
                            SmartColorLamp smartColorLamp = new SmartColorLamp(line[2], line[3], line[4], Integer.parseInt(line[5]));
                            if (smartColorLamp.isControl()) {
                                Main.smartColorLamps.add(smartColorLamp);
                            }
                        } else {
                            writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                        }
                        break;
                }
            } else if (line[0].equals("Remove")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                if (SmartPlug.isThere(line[1])) {
                    Main.names.remove(line[1]);
                    SmartPlug.remove(line[1]);
                } else if (SmartLamp.isThere(line[1])) {
                    Main.names.remove(line[1]);
                    SmartLamp.remove(line[1]);
                } else if (SmartColorLamp.isThere(line[1])) {
                    Main.names.remove(line[1]);
                    SmartColorLamp.remove(line[1]);
                } else if (SmartCamera.isThere(line[1])) {
                    Main.names.remove(line[1]);
                    SmartCamera.remove(line[1]);
                } else {
                    writeToFile(Main.outputFile, "ERROR: There is not such a device!", true, true);
                }
            } else if (line[0].equals("PlugIn")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                if (!Main.names.contains(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: There is not such a device!", true, true);
                } else if (!SmartPlug.isThere(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: This device is not a smart plug!", true, true);
                } else {
                    SmartPlug.PlugIn(line[1], Double.parseDouble(line[2]));
                }
            } else if (line[0].equals("PlugOut")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                if (!Main.names.contains(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: There is not such a device!", true, true);
                } else if (!SmartPlug.isThere(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: This device is not a smart plug!", true, true);
                } else {
                    SmartPlug.PlugOut(line[1]);
                }
            } else if (line[0].equals("Switch")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                if (!Main.names.contains(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: There is not such a device!", true, true);
                } else {
                    if (SmartPlug.isThere(line[1])) {
                        SmartPlug.switchStatus(line[1], line[2]);
                    } else if (SmartLamp.isThere(line[1])) {
                        SmartLamp.switchStatus(line[1], line[2]);
                    } else if (SmartColorLamp.isThere(line[1])) {
                        SmartColorLamp.switchStatus(line[1], line[2]);
                    } else if (SmartCamera.isThere(line[1])) {
                        SmartCamera.switchStatus(line[1], line[2]);
                    }
                }
            } else if (line[0].equals("SetSwitchTime")) {
                if (line.length != 3) {
                    writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                } else if (!Main.names.contains(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: There is not such a device!", true, true);
                }
                try {
                    writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                    LocalDateTime switchTime = LocalDateTime.parse(line[2], formatter);
                    if (switchTime.format(formatter).compareTo(initial.getTime().format(formatter)) < 0) {
                        writeToFile(Main.outputFile, "ERROR: Switch time cannot be in the past!", true, true);
                    } else {
                        if (SmartPlug.isThere(line[1])) {
                            for (SmartPlug plug : Main.smartPlugs) {
                                if (plug.getName().equals(line[1])) {
                                    plug.setSwitchTime(switchTime);
                                    Main.switchTimesArray.add(plug.getName());
                                }
                            }
                        } else if (SmartLamp.isThere(line[1])) {
                            for (SmartLamp lamp : Main.smartLamps) {
                                if (lamp.getName().equals(line[1])) {
                                    lamp.setSwitchTime(switchTime);
                                    Main.switchTimesArray.add(lamp.getName());
                                }
                            }
                        } else if (SmartColorLamp.isThere(line[1])) {
                            for (SmartColorLamp clamp : Main.smartColorLamps) {
                                if (clamp.getName().equals(line[1])) {
                                    clamp.setSwitchTime(switchTime);
                                    Main.switchTimesArray.add(clamp.getName());
                                }
                            }
                        } else if (SmartCamera.isThere(line[1])) {
                            for (SmartCamera camera : Main.smartCameras) {
                                if (camera.getName().equals(line[1])) {
                                    camera.setSwitchTime(switchTime);
                                    Main.switchTimesArray.add(camera.getName());
                                }
                            }
                        }
                    }
                } catch (DateTimeException e) {
                    writeToFile(Main.outputFile, "ERROR: Time format is not correct!", true, true);
                }
            } else if (line[0].equals("ChangeName")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                if (line.length != 3) {
                    writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
                } else if (line[1].compareTo(line[2]) == 0) {
                    writeToFile(Main.outputFile, "ERROR: Both of the names are the same, nothing changed!", true, true);
                } else if (Main.names.contains(line[1]) && Main.names.contains(line[2])) {
                    writeToFile(Main.outputFile, "ERROR: There is already a smart device with same name!", true, true);
                } else if (!Main.names.contains(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: There is not such a device!", true, true);
                } else if (Main.names.contains(line[1]) && !Main.names.contains(line[2])) {
                    for (SmartPlug plug : Main.smartPlugs) {
                        if (plug.getName().equals(line[1])) {
                            plug.setName(line[2]);
                            Main.names.set(Main.names.indexOf(line[1]), line[2]);
                        }
                    }
                    for (SmartLamp lamp : Main.smartLamps) {
                        if (lamp.getName().equals(line[1])) {
                            lamp.setName(line[2]);
                            Main.names.set(Main.names.indexOf(line[1]), line[2]);
                        }
                    }
                    for (SmartColorLamp cLamp : Main.smartColorLamps) {
                        if (cLamp.getName().equals(line[1])) {
                            cLamp.setName(line[2]);
                            Main.names.set(Main.names.indexOf(line[1]), line[2]);
                        }
                    }
                    for (SmartCamera camera : Main.smartCameras) {
                        if (camera.getName().equals(line[1])) {
                            camera.setName(line[2]);
                            Main.names.set(Main.names.indexOf(line[1]), line[2]);
                        }
                    }
                }
            } else if (line[0].equals("SetColorCode")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                boolean ctrl = true;
                for (SmartColorLamp cLamp : Main.smartColorLamps) {
                    if (cLamp.getName().equals(line[1])) {
                        ctrl = false;
                        cLamp.setColorCode(line[2]);
                    }
                }
                if (ctrl) {
                    writeToFile(Main.outputFile, "ERROR: This device is not a smart color lamp!", true, true);
                }
            } else if (line[0].equals("SetColor")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                if (SmartColorLamp.isThere(line[1])) {
                    for (SmartColorLamp clamp : Main.smartColorLamps) {
                        if (clamp.getName().equals(line[1])) {
                            String previousColorCode = clamp.getColorCode();
                            int previousBrightness = clamp.getBrightness();
                            clamp.setColorCode(line[2]);
                            clamp.setBrightness(Integer.parseInt(line[3]));
                            if (!isOk) {
                                clamp.setColorCode(previousColorCode);
                                clamp.setBrightness(previousBrightness);
                            }
                        }
                    }
                } else {
                    writeToFile(Main.outputFile, "ERROR: This device is not a smart color lamp!", true, true);
                }
            } else if (line[0].equals("SetKelvin")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                if (!SmartLamp.isThere(line[1]) && !SmartColorLamp.isThere(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: This device is not a smart lamp!", true, true);
                } else if (SmartLamp.isThere(line[1])) {
                    for (SmartLamp lamp : Main.smartLamps) {
                        if (lamp.getName().equals(line[1])) {
                            lamp.setKelvin(Integer.parseInt(line[2]));
                        }
                    }
                } else if (SmartColorLamp.isThere(line[1])) {
                    for (SmartColorLamp clamp : Main.smartColorLamps) {
                        if (clamp.getName().equals(line[1])) {
                            clamp.setKelvin(Integer.parseInt(line[2]));
                            clamp.setColorCode(null);
                        }
                    }
                }
            } else if (line[0].equals("SetWhite")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                if (!SmartLamp.isThere(line[1]) && !SmartColorLamp.isThere(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: This device is not a smart lamp!", true, true);
                } else if (SmartLamp.isThere(line[1])) {
                    for (SmartLamp lamp : Main.smartLamps) {
                        if (lamp.getName().equals(line[1])) {
                            lamp.setKelvin(Integer.parseInt(line[2]));
                            lamp.setBrightness(Integer.parseInt(line[3]));
                        }
                    }
                } else if (SmartColorLamp.isThere(line[1])) {
                    for (SmartColorLamp clamp : Main.smartColorLamps) {
                        if (clamp.getName().equals(line[1])) {
                            clamp.setKelvin(Integer.parseInt(line[2]));
                            clamp.setColorCode(null);
                            clamp.setBrightness(Integer.parseInt(line[3]));
                        }
                    }
                }
            } else if (line[0].equals("SetBrightness")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                if (!SmartLamp.isThere(line[1]) && !SmartColorLamp.isThere(line[1])) {
                    writeToFile(Main.outputFile, "ERROR: This device is not a smart lamp!", true, true);
                } else if (SmartLamp.isThere(line[1])) {
                    for (SmartLamp lamp : Main.smartLamps) {
                        if (lamp.getName().equals(line[1])) {
                            lamp.setBrightness(Integer.parseInt(line[2]));
                        }
                    }
                } else if (SmartColorLamp.isThere(line[1])) {
                    for (SmartColorLamp clamp : Main.smartColorLamps) {
                        if (clamp.getName().equals(line[1])) {
                            clamp.setBrightness(Integer.parseInt(line[2]));
                        }
                    }
                }
            } else if (line[0].equals("ZReport")) {
                writeToFile(Main.outputFile, "COMMAND: " + inputArray[i], true, true);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                writeToFile(Main.outputFile, "Time is\t" + initial.getTime().format(formatter), true, true);
                for (String device : Main.names) {
                    if (SmartPlug.isThere(device)) {
                        SmartPlug.zReport(device);
                    } else if (SmartCamera.isThere(device)) {
                        SmartCamera.zReport(device);
                    } else if (SmartLamp.isThere(device)) {
                        SmartLamp.zReport(device);
                    } else {
                        SmartColorLamp.zReport(device);
                    }
                }
            } else {
                writeToFile(Main.outputFile, "COMMAND: " + line[0], true, true);
                writeToFile(Main.outputFile, "ERROR: Erroneous command!", true, true);
            }
        }
        if (!inputArray[inputArray.length - 1].equals("ZReport")) {
            writeToFile(Main.outputFile, "ZReport:", true, true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            writeToFile(Main.outputFile, "Time is\t" + initial.getTime().format(formatter), true, true);
            for (String device : Main.names) {
                if (SmartPlug.isThere(device)) {
                    SmartPlug.zReport(device);
                } else if (SmartCamera.isThere(device)) {
                    SmartCamera.zReport(device);
                } else if (SmartLamp.isThere(device)) {
                    SmartLamp.zReport(device);
                } else {
                    SmartColorLamp.zReport(device);
                }
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