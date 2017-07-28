import domain.datamodel.Booking;
import domain.DomainStore;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class HotelBookingManagerTest {

    private LocalDate yday = LocalDate.parse("2012-07-20");
    private LocalDate today = LocalDate.parse("2012-07-21");
    private LocalDate tomorrow = LocalDate.parse("2012-07-22");

    @Test
    public void Given_AvailableRoom_Then_ReturnTrue() {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101, 102));
        DomainStore domainStore = new DomainStore(rooms);
        BookingManager bookingManager = new HotelBookingManager(domainStore);

        assertTrue(bookingManager.isRoomAvailable(101, today));
    }

    @Test
    public void Given_NoAvailableRoom_Then_ReturnFalse() {
        DomainStore domainStore = new DomainStore(null);
        BookingManager bookingManager = new HotelBookingManager(domainStore);

        assertFalse(bookingManager.isRoomAvailable(101, today));
    }

    @Test
    public void Given_RoomExistsButDatesAreBooked_Then_ReturnFalse() {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101, 102));
        DomainStore domainStore = new DomainStore(rooms);
        domainStore.addBooking(new Booking("Bobby Tables", 101, today));
        BookingManager bookingManager = new HotelBookingManager(domainStore);

        assertFalse(bookingManager.isRoomAvailable(101, today));
    }

    @Test
    public void Given_AvailableRoomExists_AndOtherDatesAndRoomsBooked_Then_ReturnTrue() {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101, 102));
        DomainStore domainStore = new DomainStore(rooms);
        domainStore.addBooking(new Booking("Bobby Tables", 101, yday));
        domainStore.addBooking(new Booking("Bobby Tables", 101, tomorrow));
        domainStore.addBooking(new Booking("Bobby Tables", 102, today));
        domainStore.addBooking(new Booking("Bobby Tables", 102, tomorrow));
        BookingManager bookingManager = new HotelBookingManager(domainStore);

        assertTrue(bookingManager.isRoomAvailable(101, today));
    }

    @Test(expected = NoRoomsAvailableException.class)
    public void Given_NoBookingsAndNoRoomExists_Then_ThrowException() throws NoRoomsAvailableException {
        DomainStore domainStore = new DomainStore(null);
        BookingManager bookingManager = new HotelBookingManager(domainStore);
        bookingManager.addBooking("BobbyTables", 101, today);
    }

    @Test
    public void Given_RoomsExistWithNoBookings_Then_AddBooking() throws NoRoomsAvailableException {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101));
        DomainStore domainStore = new DomainStore(rooms);
        BookingManager bookingManager = new HotelBookingManager(domainStore);

        bookingManager.addBooking("BobbyTables", 101, today);
        List<Booking> bookings = domainStore.getBookings();

        assertThat(bookings.size(), is(1));
        assertThat(bookings.get(0).getDate(), is(today));
        assertThat(bookings.get(0).getRoomNumber(), is(101));
        assertThat(bookings.get(0).getGuestName(), is("BobbyTables"));
    }

    @Test(expected = NoRoomsAvailableException.class)
    public void Given_RoomsExistButAlreadyHasBookingOnDate_Then_ThrowException() throws NoRoomsAvailableException {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101));
        DomainStore domainStore = new DomainStore(rooms);
        domainStore.addBooking(new Booking("BobbyTables", 101, today));
        BookingManager bookingManager = new HotelBookingManager(domainStore);
        bookingManager.addBooking("BobbyTables", 101, today);
    }

    @Test
    public void Given_NoRoomsExist_Then_ReturnEmptyList() {
        DomainStore domainStore = new DomainStore(null);
        BookingManager bookingManager = new HotelBookingManager(domainStore);
        assertThat(bookingManager.getAvailableRooms(today), is(new ArrayList<>()));
    }

    @Test
    public void Given_RoomExistsWithNoBookings_Then_ReturnAvailableRooms() {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101));
        DomainStore domainStore = new DomainStore(rooms);
        domainStore.addBooking(new Booking("BobbyTables", 101, yday));
        domainStore.addBooking(new Booking("BobbyTables", 101, tomorrow));
        domainStore.addBooking(new Booking("BobbyTables", 102, today));
        BookingManager bookingManager = new HotelBookingManager(domainStore);
        assertThat(bookingManager.getAvailableRooms(today), is(Arrays.asList(101)));
    }

    @Test
    public void Given_RoomExistsButHasBookings_Then_ReturnEmptyList() {
        Set<Integer> rooms = new HashSet<>(Arrays.asList(101));
        DomainStore domainStore = new DomainStore(rooms);
        domainStore.addBooking(new Booking("BobbyTables", 101, today));
        BookingManager bookingManager = new HotelBookingManager(domainStore);
        assertThat(bookingManager.getAvailableRooms(today), is(new ArrayList<>()));
    }
}