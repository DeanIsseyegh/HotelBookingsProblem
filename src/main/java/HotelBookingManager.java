import domain.datamodel.Booking;
import domain.DomainStore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelBookingManager implements BookingManager {

    private DomainStore domainStore;

    HotelBookingManager(DomainStore domainStore) {
        this.domainStore = domainStore;
    }

    @Override
    public boolean isRoomAvailable(Integer room, LocalDate date) {
        Boolean isAvailable = false;
        if (domainStore.doesRoomExist(room)) {
            isAvailable = !doesRoomHaveBookingOnDate(room, date);
        }
        return isAvailable;
    }

    @Override
    public synchronized void addBooking(String guest, Integer room, LocalDate date) throws NoRoomsAvailableException {
        if (domainStore.doesRoomExist(room) && !doesRoomHaveBookingOnDate(room, date)) {
            domainStore.addBooking(new Booking(guest, room, date));
        } else {
            throw new NoRoomsAvailableException();
        }
    }

    @Override
    public Iterable<Integer> getAvailableRooms(LocalDate date) {
        Set<Integer> availableRooms = new HashSet<>(domainStore.getRooms());
        availableRooms.removeIf((Integer room) -> doesRoomHaveBookingOnDate(room, date));
        return availableRooms;
    }

    private Boolean doesRoomHaveBookingOnDate(Integer room, LocalDate date) {
        List<Booking> threadSafeBookings = new ArrayList<>(domainStore.getBookingsForRoom(room));
        for (Booking booking : threadSafeBookings) {
            if (booking.isOn(room, date)) {
                return true;
            }
        }
        return false;
    }

}
