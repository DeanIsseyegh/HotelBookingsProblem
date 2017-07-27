import domain.DomainStore;

import java.time.LocalDate;
import java.util.*;

public class Main {

    static final DomainStore threadSafeDatabase = new DomainStore(Arrays.asList(101));

    public static void main(String args[]) throws Exception {
        BookingManager bm = new HotelBookingManager(threadSafeDatabase);

        synchronized (threadSafeDatabase) {
            LocalDate today = LocalDate.parse("2012-07-21");
            System.out.println(bm.isRoomAvailable(101, today)); // outputs true
            bm.addBooking("Smith", 101, today);
            System.out.println(bm.isRoomAvailable(101, today)); // outputs false
            try {
                bm.addBooking("Jones", 101, today); // throws an exception
            } catch (NoRoomsAvailableException e) {
                System.out.println("No rooms available exception caught!");
            }
        }
    }
}
