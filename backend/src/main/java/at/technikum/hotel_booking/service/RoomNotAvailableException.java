package at.technikum.hotel_booking.service;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String message){
        super(message);
    }
}
