package at.technikum.hotelbooking.service;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String message){
        super(message);
    }
}
