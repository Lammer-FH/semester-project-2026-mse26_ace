package at.technikum.hotel_booking.infrastructure.repository;

import at.technikum.hotel_booking.domain.model.BookingPeriod;
import at.technikum.hotel_booking.domain.port.BookingRepository;
import at.technikum.hotel_booking.infrastructure.entity.BookingEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingRepositoryAdapter implements BookingRepository {

    private final BookingJpaRepository bookingJpaRepository;

    public BookingRepositoryAdapter(BookingJpaRepository bookingJpaRepository) {
        this.bookingJpaRepository = bookingJpaRepository;
    }

    @Override
    public List<BookingPeriod> findOverlappingBookings(
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate
    ) {
        return bookingJpaRepository
                .findOverlappingBookings(roomId, checkInDate, checkOutDate)
                .stream()
                .map(this::toBookingPeriod)
                .toList();
    }

    private BookingPeriod toBookingPeriod(BookingEntity bookingEntity) {
        return new BookingPeriod(
                bookingEntity.getCheckInDate(),
                bookingEntity.getCheckOutDate()
        );
    }
}