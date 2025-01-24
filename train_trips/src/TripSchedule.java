import java.util.Arrays;

public class TripSchedule {
    public Trip[] trips;

    public int getLength() {
        return trips.length;
    }

    public TripSchedule(int size) {
        this.trips = new Trip[size];
    }

    public void addTrip(int index, Trip trip) {
        this.trips[index] = trip;
    }

    @Override
    public String toString() {
        return "TripSchedule{" +
                "trips=" + Arrays.toString(trips) +
                '}';
    }
    public Trip getTrip(int index) {
        return this.trips[index];
    }
    public void setTrip(int index, Trip trip) {
        this.trips[index] = trip;
    }
}