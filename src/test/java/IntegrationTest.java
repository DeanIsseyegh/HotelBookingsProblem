import domain.DomainStore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.Arrays;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * BDD outside in test
 */
public class IntegrationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void Integration_Test() throws NoRoomsAvailableException {
        DomainStore fakeDB = new DomainStore(Arrays.asList(101));
        BookingManager bm = new HotelBookingManager(fakeDB);
        LocalDate today = LocalDate.parse("2012-07-21");
        assertTrue(bm.isRoomAvailable(101, today));
        bm.addBooking("Smith", 101, today);
        assertFalse(bm.isRoomAvailable(101, today));
        thrown.expect(NoRoomsAvailableException.class);
        bm.addBooking("Jones", 101, today); // throws an exception
    }

}
