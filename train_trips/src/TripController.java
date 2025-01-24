import java.text.ParseException;

public class TripController implements DepartureController, ArrivalController {

    protected TripSchedule trip_schedule;
    public TripSchedule trips;

    @Override
    public void ArrivalSchedule(TripSchedule trips) throws ParseException {
        for (int i = 0; i < trips.getLength(); i++) {
            for (int j = 1; j < (trips.getLength() - i); j++) {
                if (trips.getTrip(j - 1).getArrivalTime().getTime() > trips.getTrip(j).getArrivalTime().getTime()) {
                    Trip temp = trips.getTrip(j - 1);
                    trips.setTrip(j - 1, trips.getTrip(j));
                    trips.setTrip(j, temp);
                } else if (trips.getTrip(j - 1).getArrivalTime().getTime() == trips.getTrip(j).getArrivalTime().getTime()) {
                    trips.getTrip(j).setState("Delayed");
                    trips.getTrip(j - 1).setState("Delayed");
                }
            }
        }
    }

    @Override
    public void DepartureSchedule(TripSchedule trips) {
        for (int i = 0; i < trips.getLength(); i++) {
            for (int j = 1; j < (trips.getLength() - i); j++) {
                if (trips.getTrip(j - 1).getDepartureTime().getTime() > trips.getTrip(j).getDepartureTime().getTime()) {
                    Trip temp = trips.getTrip(j - 1);
                    trips.setTrip(j - 1, trips.getTrip(j));
                    trips.setTrip(j, temp);
                } else if (trips.getTrip(j - 1).getDepartureTime().getTime() == trips.getTrip(j).getDepartureTime().getTime()) {
                    trips.getTrip(j).setState("Delayed");
                    trips.getTrip(j - 1).setState("Delayed");
                }
            }
        }
    }
}