package at.technikum.hotelbooking.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import at.technikum.hotelbooking.infrastructure.entity.BookingEntity;

public interface BookingJpaRepository extends JpaRepository<BookingEntity, Long> {

    @Query("""
        SELECT booking
        FROM BookingEntity booking
        WHERE booking.room.id = :roomId
        AND booking.checkIn < :checkOutDate
        AND booking.checkOut > :checkInDate
    """)
    List<BookingEntity> findOverlappingBookings(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );
}