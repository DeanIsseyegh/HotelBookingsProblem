package domain.datamodel;

import data.domain.Booking;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BookingTest {

    private LocalDate today = LocalDate.parse("2012-07-21");

    @Test
    public void getGuestName() {
        Booking booking = new Booking("BobbyTables", 101, today);
        assertThat(booking.getGuestName(), is("BobbyTables"));
    }

    @Test
    public void getRoomNumber() {
        Booking booking = new Booking("BobbyTables", 101, today);
        assertThat(booking.getRoomNumber(), is(101));
    }

    @Test
    public void getDate() {
        Booking booking = new Booking("BobbyTables", 101, today);
        assertThat(booking.getDate(), is(today));
    }

    @Test
    public void Given_BookingIsOnASpecificRoomAndDate_Then_ReturnTrue() {
        Booking booking = new Booking("BobbyTables", 101, today);
        assertTrue(booking.isOn(101, today));
    }

}