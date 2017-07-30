package service;

public class RoomNotAvailableException extends Exception {
    RoomNotAvailableException(String message) {
        super(message);
    }
}
