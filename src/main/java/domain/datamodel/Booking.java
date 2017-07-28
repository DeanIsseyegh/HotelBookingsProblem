package domain.datamodel;

import java.time.LocalDate;

public class Booking {

    private Integer roomNumber;
    private LocalDate date;
    private String guestName;

    public Booking(String guestName, Integer roomNumber, LocalDate date) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.date = date;
    }

    public String getGuestName() {
        return guestName;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public Boolean isOn(Integer room, LocalDate date) {
        return (getRoomNumber() == room && getDate() == date);
    }

}
