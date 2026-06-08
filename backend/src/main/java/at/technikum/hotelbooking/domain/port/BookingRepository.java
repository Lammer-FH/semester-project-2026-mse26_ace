package at.technikum.hotelbooking.domain.port;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import at.technikum.hotelbooking.domain.model.Booking;
import at.technikum.hotelbooking.domain.model.BookingPeriod;

public interface BookingRepository{
    List<BookingPeriod> findOverlappingBookings(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);
    Booking save(Booking booking);
    Optional<Booking> findById(Long id);
}