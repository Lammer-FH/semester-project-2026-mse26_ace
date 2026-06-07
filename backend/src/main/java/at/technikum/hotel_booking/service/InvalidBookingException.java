package at.technikum.hotel_booking.service;

public class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(String message){
        super(message);
    }
}
