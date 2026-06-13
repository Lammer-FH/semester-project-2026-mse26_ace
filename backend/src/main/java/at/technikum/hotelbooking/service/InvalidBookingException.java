package at.technikum.hotelbooking.service;

public class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(String message){
        super(message);
    }
}
