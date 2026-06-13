package at.technikum.hotelbooking.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import at.technikum.hotelbooking.domain.model.Booking;
import at.technikum.hotelbooking.domain.model.BookingPeriod;
import at.technikum.hotelbooking.domain.port.BookingRepository;
import at.technikum.hotelbooking.infrastructure.entity.BookingEntity;

@Component
public class BookingRepositoryAdapter implements BookingRepository {

    private final RoomJpaRepository roomJpaRepository;
    private final BookingJpaRepository bookingJpaRepository;

    public BookingRepositoryAdapter(BookingJpaRepository bookingJpaRepository, RoomJpaRepository roomJpaRepository) {
        this.bookingJpaRepository = bookingJpaRepository;
        this.roomJpaRepository = roomJpaRepository;
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
            bookingEntity.getCheckIn(),
            bookingEntity.getCheckOut()
    );
}

    @Override   
    public Booking save(Booking booking) {
        BookingEntity entity = new BookingEntity();
        entity.setRoom(roomJpaRepository.findById(booking.getRoomId()).orElseThrow());
        entity.setCheckIn(booking.getCheckIn());
        entity.setCheckOut(booking.getCheckOut());
        entity.setFirstName(booking.getFirstName());
        entity.setLastName(booking.getLastName());
        entity.setEmail(booking.getEmail());
        entity.setBreakfast(booking.isBreakfast());
        entity.setPriceAtBooking(booking.getPriceAtBooking());

        BookingEntity saved = bookingJpaRepository.save(entity);

        return new Booking(
            saved.getId(),
            saved.getRoom().getId(),
            saved.getCheckIn(),
            saved.getCheckOut(),
            saved.getFirstName(),
            saved.getLastName(),
            saved.getEmail(),
            saved.isBreakfast(),
            saved.getPriceAtBooking(),
            saved.getCreatedAt()
        );
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingJpaRepository.findById(id)
            .map(entity -> new Booking(
                entity.getId(), 
                entity.getRoom().getId(), 
                entity.getCheckIn(), 
                entity.getCheckOut(), 
                entity.getFirstName(), 
                entity.getLastName(),
                entity.getEmail(), 
                entity.isBreakfast(),
                entity.getPriceAtBooking(),
                entity.getCreatedAt()
            ));
    }
}