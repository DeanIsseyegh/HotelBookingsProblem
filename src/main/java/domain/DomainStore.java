package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DomainStore {

    private List<Booking> bookings = new ArrayList<>();
    private Set<Integer> rooms = new HashSet<>();

    public DomainStore(Set<Integer> rooms) {
        this.rooms = (rooms != null) ? rooms : this.rooms;
    }

    public Set<Integer> getRooms() {
        return rooms;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

}
