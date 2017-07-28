import domain.datamodel.Booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapHelper3 implements Runnable {
    BookingManager bm;

    public MapHelper3(BookingManager bm) {
        this.bm = bm;
        new Thread(this, "MapHelper3").start();
    }

    public void run() {
        LocalDate today = LocalDate.parse("2012-07-21");
        try {
            bm.addBooking("c", 103, today);
            bm.isRoomAvailable(103, today);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }
}