package at.technikum.hotel_booking.infrastructure.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.technikum.hotel_booking.domain.model.BookingPeriod;
import at.technikum.hotel_booking.domain.port.BookingRepository;

@Component
public class BookingRepositoryAdapter implements BookingRepository {

    @Override
    public List <BookingPeriod> findOverlappingBookings(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        // Implementation to fetch booked periods for the given room ID from the database
        // This is a placeholder implementation and should be replaced with actual database access code
        return new ArrayList<>();
    }
}
