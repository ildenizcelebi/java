import java.time.LocalDateTime;

/**
 * This abstract class is the superclass for all devices.
 */

public abstract class Super {
    protected String name; // Name of the device
    protected boolean control = true; // Control status of the device
    protected LocalDateTime switchTime; // Time when the device is switched on or off
    protected String switching = "Off"; // Switching status of the device

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public LocalDateTime getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    public void setSwitching(String switching) {
        this.switching = switching;
    }

    public String getSwitching() {
        return switching;
    }
}