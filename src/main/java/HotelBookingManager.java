import domain.Booking;
import domain.DomainStore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelBookingManager implements BookingManager {

    private DomainStore domainStore;

    HotelBookingManager(DomainStore domainStore) {
        this.domainStore = domainStore;
    }

    @Override
    public boolean isRoomAvailable(Integer room, LocalDate date) {
        List<Integer> rooms = domainStore.getRooms();
        List<Booking> bookings = domainStore.getBookings();
        Boolean isAvailable = false;
        if (rooms.contains(room)) {
            isAvailable = !doesRoomHaveBookingOnDate(room, date, bookings);
        }
        return isAvailable;
    }

    @Override
    public void addBooking(String guest, Integer room, LocalDate date) throws NoRoomsAvailableException{
        List<Integer> rooms = domainStore.getRooms();
        List<Booking> bookings = domainStore.getBookings();
        if (rooms.contains(room) && !doesRoomHaveBookingOnDate(room, date, bookings)) {
            domainStore.addBooking(new Booking(guest, room, date));
        } else {
            throw new NoRoomsAvailableException();
        }
    }

    @Override
    public Iterable<Integer> getAvailableRooms(LocalDate date) {
        ArrayList<Integer> availableRooms = new ArrayList<>(domainStore.getRooms());
        List<Booking> bookings = domainStore.getBookings();
        availableRooms.removeIf((Integer room) -> doesRoomHaveBookingOnDate(room, date, bookings));
        return availableRooms;
    }

    private Boolean doesRoomHaveBookingOnDate(Integer room, LocalDate date, List<Booking> existingBookings) {
        for (Booking booking : existingBookings) {
            if (booking.isOn(room, date)) {
                return true;
            }
        }
        return false;
    }

}
