import data.DomainStore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.BookingManager;
import service.HotelBookingManager;
import service.RoomNotAvailableException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class IntegrationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void Integration_Test() throws RoomNotAvailableException {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101));
        DomainStore fakeDB = new DomainStore(rooms);
        BookingManager bm = new HotelBookingManager(fakeDB);
        LocalDate today = LocalDate.parse("2012-07-21");
        assertTrue(bm.isRoomAvailable(101, today));
        bm.addBooking("Smith", 101, today);
        assertFalse(bm.isRoomAvailable(101, today));
        thrown.expect(RoomNotAvailableException.class);
        bm.addBooking("Jones", 101, today); // throws an exception
    }

}
