package at.technikum.hotel_booking.service;

import java.math.BigDecimal;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import at.technikum.hotel_booking.domain.port.BookingRepository;
import at.technikum.hotel_booking.domain.port.RoomRepository;
import at.technikum.hotel_booking.domain.model.Booking;
import at.technikum.hotel_booking.domain.model.Room;

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
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

        Room room = roomRepository.findById(booking.getRoomId())
            .orElseThrow(()-> new RoomNotFoundException("Room with id "+booking.getRoomId()+" not found"));
        
        long nights = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
        
        Booking withPrice = new Booking(
            booking.getId(),
            booking.getRoomId(),
            booking.getCheckIn(),
            booking.getCheckOut(),
            booking.getFirstName(),
            booking.getLastName(),
            booking.getEmail(),
            booking.isBreakfast(),
            totalPrice,
            booking.getCreatedAt()
        );

        return bookingRepository.save(withPrice);
    }

    public Booking getBookingById(Long id){
        return bookingRepository.findById(id)
            .orElseThrow(() -> new BookingNotFoundException("Booking with id "+id+" not found"));
    }

}
