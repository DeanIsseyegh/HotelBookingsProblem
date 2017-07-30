package service;

import data.domain.Booking;
import data.DomainStore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelBookingManager implements BookingManager {

    private DomainStore domainStore;

    public HotelBookingManager(DomainStore domainStore) {
        this.domainStore = domainStore;
    }

    @Override
    public boolean isRoomAvailable(Integer room, LocalDate date) {
        return (domainStore.doesRoomExist(room) && !doesRoomHaveBookingOnDate(room, date));
    }

    @Override
    public synchronized void addBooking(String guest, Integer room, LocalDate date) throws RoomNotAvailableException {
        if (isRoomAvailable(room, date)) {
            domainStore.addBooking(new Booking(guest, room, date));
        } else {
            String exceptionMessage = String.format("No room available for room %d on date %s for guest %s", room, date.toString(), guest);
            throw new RoomNotAvailableException(exceptionMessage);
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
