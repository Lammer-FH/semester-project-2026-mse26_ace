package at.technikum.hotel_booking.service;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message){
        super(message);
    }
}
