package at.technikum.hotelbooking.service;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message){
        super(message);
    }
}
