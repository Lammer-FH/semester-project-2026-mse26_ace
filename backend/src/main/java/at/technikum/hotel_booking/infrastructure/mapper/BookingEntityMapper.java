package at.technikum.hotel_booking.infrastructure.mapper;

import at.technikum.hotel_booking.domain.model.Booking;
import at.technikum.hotel_booking.web.dto.BookingResponse;
import at.technikum.hotel_booking.web.dto.CreateBookingRequest;

public final class BookingEntityMapper {
    
    private BookingEntityMapper(){

    }

    public static Booking toDomain(CreateBookingRequest request){
        return new Booking(
            null, //DB vergibt sie
            request.roomId(),
            request.checkIn(), 
            request.checkOut(), 
            request.firstName(), 
            request.lastName(), 
            request.email(), 
            request.breakfast()
        );
    }

    public static BookingResponse toResponse(Booking booking){
        return new BookingResponse(
            booking.getId(),
            booking.getRoomId(),
            booking.getCheckIn(),
            booking.getCheckOut(),
            booking.getFirstName(),
            booking.getLastName(),
            booking.getEmail(),
            booking.isBreakfast()
        );
    }
}
