package domain;

import domain.datamodel.Booking;

import java.util.*;

public class DomainStore {

    private Map<Integer, List<Booking>> roomsAndBookings = new HashMap<>();

    public DomainStore(Set<Integer> rooms) {
        setupHotelRooms(rooms);
    }

    private void setupHotelRooms(Set<Integer> rooms) {
        if (rooms != null) {
            rooms.forEach(item -> roomsAndBookings.put(item, new ArrayList<Booking>()));
        }
    }

    public Set<Integer> getRooms() {
        return roomsAndBookings.keySet();
    }

    public Boolean doesRoomExist(Integer room) {
        return (roomsAndBookings.get(room) != null);
    }

    public void addBooking(Booking booking) {
        if (roomsAndBookings.get(booking.getRoomNumber()) != null) {
            roomsAndBookings.get(booking.getRoomNumber()).add(booking);
        }
    }

    public List<Booking> getBookingsForRoom(Integer roomNumber) {
        return roomsAndBookings.get(roomNumber);
    }

    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        roomsAndBookings.values().forEach(item -> bookings.addAll(item.subList(0, item.size())));
        return bookings;
    }

}
