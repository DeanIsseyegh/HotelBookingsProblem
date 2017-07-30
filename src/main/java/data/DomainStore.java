package data;

import data.domain.Booking;

import java.util.*;

public class DomainStore {

    private Map<Integer, List<Booking>> roomsAndBookings = Collections.synchronizedMap(new HashMap<>());

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
        List<Booking> allBookings = new ArrayList<>();
        roomsAndBookings.values().forEach(item -> allBookings.addAll(item.subList(0, item.size())));
        return allBookings;
    }

}
