package domain;

import java.util.ArrayList;
import java.util.List;

public class DomainStore {

    private List<Booking> bookings = new ArrayList<>();
    private List<Integer> rooms = new ArrayList<>();

    public DomainStore(List<Integer> rooms) {
        this.rooms = (rooms != null) ? rooms : this.rooms;
    }

    public List<Integer> getRooms() {
        return rooms;
    }

    public synchronized void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

}
