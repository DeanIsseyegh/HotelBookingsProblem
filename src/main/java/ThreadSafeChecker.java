import com.sun.java.browser.plugin2.DOM;
import data.DomainStore;
import service.BookingManager;
import service.HotelBookingManager;
import service.RoomNotAvailableException;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadSafeChecker {

    static final DomainStore DOMAIN_STORE = new DomainStore(new HashSet<>(Arrays.asList(101, 102, 103, 104)));
    static final BookingManager BM = new HotelBookingManager(DOMAIN_STORE);

    public static void main(String args[]) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List list = new ArrayList<>();
        Callable<String> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(10);
            new TypicalHotelManagerActions(BM).run();
            return "Task's execution";
        };
        for (int i = 0; i < 10000; i++) {
            list.add(callableTask);
        }
        executorService.invokeAll(list);
    }


}

class TypicalHotelManagerActions implements Runnable {
    BookingManager bm;

    static final LocalDate tomorrow = LocalDate.parse("2012-07-22");

    TypicalHotelManagerActions(BookingManager bm) {
        this.bm = bm;
    }

    public void run() {
        LocalDate today = LocalDate.parse("2012-07-21");
        try {
            try {
                bm.addBooking("Jones", 105, today); // throws an exception
            } catch (RoomNotAvailableException e) {
            }
            bm.getAvailableRooms(today);
            bm.getAvailableRooms(tomorrow);
            try {
                bm.addBooking("Jones", 103, today); // throws an exception
                bm.addBooking("Jones", 101, today); // throws an exception
                bm.addBooking("Bob", 101, tomorrow); // throws an exception
                bm.addBooking("Jones", 101, tomorrow); // throws an exception
                bm.getAvailableRooms(today);
                bm.addBooking("Jones", 101, tomorrow); // throws an exception
                bm.addBooking("Jones", 104, today); // throws an exception
                bm.addBooking("Jones", 102, today); // throws an exception
            } catch (RoomNotAvailableException e) {
            }
            bm.isRoomAvailable(104, today);
            bm.isRoomAvailable(101, today);
            bm.isRoomAvailable(101, tomorrow);
            bm.isRoomAvailable(102, today);
            bm.isRoomAvailable(103, today);
            try {
                bm.addBooking("Jones", 101, today); // throws an exception
            } catch (RoomNotAvailableException e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Thread Finished");
    }
}
