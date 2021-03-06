import data.DomainStore;
import service.BookingManager;
import service.HotelBookingManager;
import service.RoomNotAvailableException;

import java.time.LocalDate;
import java.util.*;

public class Main {

    static final DomainStore DOMAIN_STORE = new DomainStore(new HashSet<>(Arrays.asList(101, 102, 103, 104)));

    public static void main(String args[]) throws Exception {
        BookingManager bm = new HotelBookingManager(DOMAIN_STORE);

        LocalDate today = LocalDate.parse("2012-07-21");
        System.out.println(bm.isRoomAvailable(101, today)); // outputs true
        bm.addBooking("Smith", 101, today);
        System.out.println(bm.isRoomAvailable(101, today)); // outputs false
        try {
            bm.addBooking("Jones", 101, today); // throws an exception
        } catch (RoomNotAvailableException e) {
            System.out.println("No rooms available exception caught!");
            e.printStackTrace();
        }
        System.out.println("Available rooms are : " + bm.getAvailableRooms(today));
    }
}
