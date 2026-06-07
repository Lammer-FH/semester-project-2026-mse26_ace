package at.technikum.hotel_booking.domain.port;
import java.time.LocalDate;
import java.util.List;

import at.technikum.hotel_booking.domain.model.Booking;
import at.technikum.hotel_booking.domain.model.BookingPeriod;

public interface BookingRepository{
    List<BookingPeriod> findOverlappingBookings(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);
    Booking save(Booking booking);
}