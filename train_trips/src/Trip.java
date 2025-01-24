import java.util.Calendar;
import java.util.Date;

public class Trip {
    public String tripName;
    public Date departureTime;
    public Date arrivalTime;
    public int duration;
    public String state;

    public void calculateArrival(){
        Calendar calender = Calendar.getInstance();
        calender.setTime(departureTime);
        calender.add(Calendar.MINUTE, duration);
        arrivalTime = calender.getTime();
    }

    public Trip(String tripName, Date departureTime, int duration) {
        this.tripName = tripName;
        this.departureTime = departureTime;
        this.duration = duration;
        this.state = "IDLE";
        calculateArrival();
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripName='" + tripName + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", duration=" + duration +
                ", state='" + state + '\'' +
                '}';
    }

    public String getTripName() {
        return tripName;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}