import domain.datamodel.Booking;
import domain.DomainStore;

import java.time.LocalDate;
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
            isAvailable = !doesRoomHaveBookingOnDate(room, date, domainStore.getBookingsForRoom(room));
        }
        return isAvailable;
    }

    @Override
    public void addBooking(String guest, Integer room, LocalDate date) throws NoRoomsAvailableException{
        if (domainStore.doesRoomExist(room) && !doesRoomHaveBookingOnDate(room, date, domainStore.getBookingsForRoom(room))) {
            domainStore.addBooking(new Booking(guest, room, date));
        } else {
            throw new NoRoomsAvailableException();
        }
    }

    @Override
    public Iterable<Integer> getAvailableRooms(LocalDate date) {
        Set<Integer> availableRooms = domainStore.getRooms();
        availableRooms.removeIf((Integer room) -> doesRoomHaveBookingOnDate(room, date, domainStore.getBookingsForRoom(room)));
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
