import com.sun.java.browser.plugin2.DOM;
import data.DomainStore;
import service.BookingManager;
import service.HotelBookingManager;
import service.RoomNotAvailableException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSafeChecker {

    static final DomainStore DOMAIN_STORE = new DomainStore(new HashSet<>(Arrays.asList(101, 102, 103, 104)));
    static final BookingManager BM = new HotelBookingManager(DOMAIN_STORE);


    public static void main(String args[]) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.submit(new TypicalHotelManagerActions(BM, DOMAIN_STORE));
            executorService.submit(new TypicalHotelManagerActions(BM, DOMAIN_STORE));
        }
    }


}

class TypicalHotelManagerActions implements Runnable {
    BookingManager bm;
    DomainStore ds;
    static final LocalDate today = LocalDate.parse("2012-07-21");
    static final LocalDate tomorrow = LocalDate.parse("2012-07-22");

    TypicalHotelManagerActions(BookingManager bm, DomainStore ds) {
        this.bm = bm;
        this.ds = ds;
    }

    public void run() {

        try {
            Thread.sleep(100);
            ds.getRooms();
            try {
                bm.addBooking("Jones", 101, today); // throws an exception
            } catch (RoomNotAvailableException e) {
            }
            ds.getRooms();
            bm.getAvailableRooms(today);
            bm.getAvailableRooms(tomorrow);
            try {
                bm.addBooking("Jones", 101, today); // throws an exception
            } catch (RoomNotAvailableException e) {
            }
            Thread.sleep(100);
            ds.getBookingsForRoom(103);
            bm.isRoomAvailable(101, today);
            bm.isRoomAvailable(101, tomorrow);
            bm.isRoomAvailable(104, today);
            try {
                bm.addBooking("Jones", 101, today); // throws an exception
            } catch (RoomNotAvailableException e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Thread finished");
    }
}
