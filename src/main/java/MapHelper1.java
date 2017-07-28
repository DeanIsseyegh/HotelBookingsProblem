import domain.datamodel.Booking;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapHelper1 implements Runnable {
    BookingManager bm;

    public MapHelper1(BookingManager bm) {
        this.bm = bm;
        new Thread(this, "MapHelper1").start();
    }

    public void run() {
        LocalDate today = LocalDate.parse("2012-07-21");
        try {
            bm.addBooking("b", 103, today);
            bm.isRoomAvailable(103, today);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }
}