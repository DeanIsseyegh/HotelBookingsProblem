import domain.DomainStore;
import domain.datamodel.Booking;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dean on 28/07/2017.
 */
public class ThreadSafeChecker {

    static final DomainStore domainStore = new DomainStore(new HashSet<>(Arrays.asList(101, 102, 103)));
    static final BookingManager bm = new HotelBookingManager(domainStore);
   // static final LocalDate today = LocalDate.parse("2012-07-21");

    public static void main(String args[]) throws Exception {
        Date b4 = new Date();
            for (int i = 0; i < 10000; i++) {
                    MapHelper1 mapHelper1 = new MapHelper1(bm);
                    MapHelper2 mapHelper2 = new MapHelper2(bm);
                    domainStore.getBookings();
                    MapHelper3 mapHelper3 = new MapHelper3(bm);
                    domainStore.getBookingsForRoom(103);
                    MapHelper4 mapHelper4 = new MapHelper4(bm);
                    domainStore.getRooms();
                    domainStore.doesRoomExist(999);
            }
        Date after = new Date();
        System.out.println(after.getTime() - b4.getTime());
        System.out.println(domainStore.getBookingsForRoom(103).size());
        System.out.println("Done");

    }
}
