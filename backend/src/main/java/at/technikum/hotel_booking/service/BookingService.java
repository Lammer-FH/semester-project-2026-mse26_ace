package at.technikum.hotel_booking.service;

import org.springframework.stereotype.Service;

import at.technikum.hotel_booking.domain.model.Booking;
import at.technikum.hotel_booking.domain.port.BookingRepository;

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking){
        if(!booking.getCheckOut().isAfter(booking.getCheckIn())){
            throw new InvalidBookingException("Check out must be after check in");
        }

        boolean hasConflict = !bookingRepository.findOverlappingBookings(
            booking.getRoomId(), 
            booking.getCheckIn(), 
            booking.getCheckOut()
        ).isEmpty();
        
        if(hasConflict){
            throw new RoomNotAvailableException("Room is not available for the selected period");
        }

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id){
        return bookingRepository.findById(id)
            .orElseThrow(() -> new BookingNotFoundException("Booking with id "+id+" not found"));
    }

}
