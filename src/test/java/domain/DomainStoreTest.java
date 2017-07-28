package domain;

import domain.datamodel.Booking;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DomainStoreTest {

    private LocalDate today = LocalDate.parse("2012-07-21");

    @Test
    public void Given_RoomsExist_ReturnsRooms() {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101));
        DomainStore domainStore = new DomainStore(rooms);

        assertEquals(rooms, domainStore.getRooms());
    }

    @Test
    public void Given_NoRooms_ReturnEmptySet() {
        DomainStore domainStore = new DomainStore(null);

        assertEquals(new HashSet<>(), domainStore.getRooms());
    }

    @Test
    public void Given_BookingsExist_ReturnBookings() {
        Booking booking = new Booking("BobbyTables", 101, today);
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101));
        DomainStore domainStore = new DomainStore(rooms);
        domainStore.addBooking(booking);

        assertEquals(Arrays.asList(booking), domainStore.getBookings());
    }

    @Test
    public void Given_NoBookings_ReturnsEmptyList() {
        DomainStore domainStore = new DomainStore(null);

        assertEquals(new ArrayList<>(), domainStore.getBookings());
    }

    @Test
    public void Given_RoomExists_Then_ReturnsTrue() {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101));
        DomainStore domainStore = new DomainStore(rooms);

        assertTrue(domainStore.doesRoomExist(101));
    }

    @Test
    public void Given_RoomDoesNotExists_Then_ReturnsFalse() {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(102, 100, 103));
        DomainStore domainStore = new DomainStore(rooms);

        assertFalse(domainStore.doesRoomExist(101));
    }
}