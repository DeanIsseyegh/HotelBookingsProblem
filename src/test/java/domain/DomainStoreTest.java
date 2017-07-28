package domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DomainStoreTest {

    private LocalDate today = LocalDate.parse("2012-07-21");

    @Test
    public void ReturnsRooms() {
        DomainStore domainStore = new DomainStore(null);
        assertEquals(new HashSet<>(), domainStore.getRooms());

        Set<Integer> rooms = new HashSet<>(Arrays.asList(101));
        domainStore = new DomainStore(rooms);
        assertEquals(rooms, domainStore.getRooms());
    }

    @Test
    public void ReturnsBookings() {
        DomainStore domainStore = new DomainStore(null);
        assertEquals(new ArrayList<>(), domainStore.getBookings());

        Booking booking = new Booking("BobbyTables", 101, today);
        domainStore.addBooking(booking);
        assertEquals(Arrays.asList(booking), domainStore.getBookings());
    }

}