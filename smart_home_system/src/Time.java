import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Time {
    private String strTime;
    private LocalDateTime time;

    public void setInitialTime(String date) {
        strTime = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        this.time = LocalDateTime.parse(date, formatter);
    }

    public void setTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        if (date.compareTo(getTime().format(formatter)) > 0) {
            this.setInitialTime(date);
            ArrayList<String> gonnaRemove = new ArrayList<>();
            if (Main.switchTimesArray.size() > 0) {
                for (int i = 0; i < Main.switchTimesArray.size(); i++) {
                    String device = Main.switchTimesArray.get(i);
                    if (SmartPlug.isThere(device)) {
                        for (SmartPlug plug : Main.smartPlugs) {
                            if (plug.getName().equals(device)) {
                                if (date.compareTo(plug.getSwitchTime().format(formatter)) >= 0) {
                                    SmartPlug.switchStatus(device, plug.getSwitching().equals("On") ? "Off" : "On");
                                    gonnaRemove.add(device);
                                }
                            }
                        }
                    } else if (SmartCamera.isThere(device)) {
                        for (SmartCamera camera : Main.smartCameras) {
                            if (camera.getName().equals(device)) {
                                if (date.compareTo(camera.getSwitchTime().format(formatter)) >= 0) {
                                    SmartCamera.switchStatus(device, camera.getSwitching().equals("On") ? "Off" : "On");
                                    gonnaRemove.add(device);
                                }
                            }
                        }
                    } else if (SmartLamp.isThere(device)) {
                        for (SmartLamp lamp : Main.smartLamps) {
                            if (lamp.getName().equals(device)) {
                                if (getTime().isAfter(lamp.getSwitchTime()) || getTime().isEqual(lamp.getSwitchTime())) {
                                    SmartLamp.switchStatus(device, lamp.getSwitching().equals("On") ? "Off" : "On");
                                    gonnaRemove.add(device);
                                }
                            }
                        }
                    } else if (SmartColorLamp.isThere(device)) {
                        for (SmartColorLamp clamp : Main.smartColorLamps) {
                            if (clamp.getName().equals(device)) {
                                if (getTime().isAfter(clamp.getSwitchTime()) || getTime().isEqual(clamp.getSwitchTime())) {
                                    SmartColorLamp.switchStatus(device, clamp.getSwitching().equals("On") ? "Off" : "On");
                                    gonnaRemove.add(device);
                                }
                            }
                        }
                    }
                }
                for (String name : gonnaRemove) {
                    Main.switchTimesArray.remove(name);
                }
            }
        } else {
            try {
                LocalDateTime wrong = LocalDateTime.parse(date, formatter);
                writeToFile(Main.outputFile, "ERROR: Time cannot be reversed!", true, true);
            } catch (DateTimeException e) {
                writeToFile(Main.outputFile, "ERROR: Time format is not correct!", true, true);
            }
        }
    }

    public void skipMinutes(int minutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        time = time.plusMinutes(minutes);
        strTime = time.format(formatter);
        ArrayList<String> goingToRemove = new ArrayList<>();
        if (Main.switchTimesArray.size() > 0) {
            for (int i = 0; i < Main.switchTimesArray.size(); i++) {
                String device = Main.switchTimesArray.get(i);
                if (SmartPlug.isThere(device)) {
                    for (SmartPlug plug : Main.smartPlugs) {
                        if (plug.getName().equals(device)) {
                            if (strTime.compareTo(plug.getSwitchTime().format(formatter)) >= 0) {
                                SmartPlug.switchStatus(device, plug.getSwitching().equals("On") ? "Off" : "On");
                                goingToRemove.add(device);
                            }
                        }
                    }
                } else if (SmartCamera.isThere(device)) {
                    for (SmartCamera camera : Main.smartCameras) {
                        if (camera.getName().equals(device)) {
                            if (strTime.compareTo(camera.getSwitchTime().format(formatter)) >= 0) {
                                SmartCamera.switchStatus(device, camera.getSwitching().equals("On") ? "Off" : "On");
                                goingToRemove.add(device);
                            }
                        }
                    }
                } else if (SmartLamp.isThere(device)) {
                    for (SmartLamp lamp : Main.smartLamps) {
                        if (lamp.getName().equals(device)) {
                            if (strTime.compareTo(lamp.getSwitchTime().format(formatter)) >= 0) {
                                SmartLamp.switchStatus(device, lamp.getSwitching().equals("On") ? "Off" : "On");
                                goingToRemove.add(device);
                            }
                        }
                    }
                } else {
                    for (SmartColorLamp clamp : Main.smartColorLamps) {
                        if (clamp.getName().equals(device)) {
                            if (strTime.compareTo(clamp.getSwitchTime().format(formatter)) >= 0) {
                                SmartColorLamp.switchStatus(device, clamp.getSwitching().equals("On") ? "Off" : "On");
                                goingToRemove.add(device);
                            }
                        }
                    }
                }
            }
            for (String name : goingToRemove) {
                Main.switchTimesArray.remove(name);
            }
        }
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void nop() {
        if (Main.switchTimesArray.size() > 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            LocalDateTime controlTime = null;
            LocalDateTime oldTime = null;
            for (int i = 0; i < Main.switchTimesArray.size(); i++) {
                String device = Main.switchTimesArray.get(i);
                if (SmartPlug.isThere(device)) {
                    for (SmartPlug plug : Main.smartPlugs) {
                        if (plug.getName().equals(device)) {
                            controlTime = plug.getSwitchTime();
                            if (i == 0) {
                                oldTime = controlTime;
                            }
                        }
                    }
                } else if (SmartCamera.isThere(device)) {
                    for (SmartCamera camera : Main.smartCameras) {
                        if (camera.getName().equals(device)) {
                            controlTime = camera.getSwitchTime();
                            if (i == 0) {
                                oldTime = controlTime;
                            }
                        }
                    }
                } else if (SmartLamp.isThere(device)) {
                    for (SmartLamp lamp : Main.smartLamps) {
                        if (lamp.getName().equals(device)) {
                            controlTime = lamp.getSwitchTime();
                            if (i == 0) {
                                oldTime = controlTime;
                            }
                        }
                    }
                } else {
                    for (SmartColorLamp clamp : Main.smartColorLamps) {
                        if (clamp.getName().equals(device)) {
                            controlTime = clamp.getSwitchTime();
                            if (i == 0) {
                                oldTime = controlTime;
                            }
                        }
                    }
                }
                if (oldTime.isAfter(controlTime)) {
                    oldTime = controlTime;
                }
            }
            ArrayList<String> willRemove = new ArrayList<>();
            for (int j = 0; j < Main.switchTimesArray.size(); j++) {
                String device = Main.switchTimesArray.get(j);
                if (SmartPlug.isThere(device)) {
                    for (SmartPlug plug : Main.smartPlugs) {
                        if (plug.getName().equals(device)) {
                            if (plug.getSwitchTime() == oldTime) {
                                Main.initial.setInitialTime(oldTime.format(formatter));
                                SmartPlug.switchStatus(device, plug.getSwitching().equals("On") ? "Off" : "On");
                                willRemove.add(device);
                            }
                        }
                    }
                } else if (SmartCamera.isThere(device)) {
                    for (SmartCamera camera : Main.smartCameras) {
                        if (camera.getName().equals(device)) {
                            if (camera.getSwitchTime() == oldTime) {
                                Main.initial.setInitialTime(oldTime.format(formatter));
                                SmartCamera.switchStatus(device, camera.getSwitching().equals("On") ? "Off" : "On");
                                willRemove.add(device);
                            }
                        }
                    }
                } else if (SmartLamp.isThere(device)) {
                    for (SmartLamp lamp : Main.smartLamps) {
                        if (lamp.getName().equals(device)) {
                            if (lamp.getSwitchTime() == oldTime) {
                                Main.initial.setInitialTime(oldTime.format(formatter));
                                SmartLamp.switchStatus(device, lamp.getSwitching().equals("On") ? "Off" : "On");
                                willRemove.add(device);
                            }
                        }
                    }
                } else {
                    for (SmartColorLamp clamp : Main.smartColorLamps) {
                        if (clamp.getName().equals(device)) {
                            if (clamp.getSwitchTime() == oldTime) {
                                Main.initial.setInitialTime(oldTime.format(formatter));
                                SmartColorLamp.switchStatus(device, clamp.getSwitching().equals("On") ? "Off" : "On");
                                willRemove.add(device);
                            }
                        }
                    }
                }
            }
            for (String name : willRemove) {
                Main.switchTimesArray.remove(name);
            }
        } else {
            writeToFile(Main.outputFile, "ERROR: There is nothing to switch!", true, true);
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