package at.technikum.hotel_booking.infrastructure.repository;

import at.technikum.hotel_booking.infrastructure.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingJpaRepository extends JpaRepository<BookingEntity, Long> {

    @Query("""
        SELECT booking
        FROM BookingEntity booking
        WHERE booking.room.id = :roomId
        AND booking.checkInDate < :checkOutDate
        AND booking.checkOutDate > :checkInDate
    """)
    List<BookingEntity> findOverlappingBookings(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );
}