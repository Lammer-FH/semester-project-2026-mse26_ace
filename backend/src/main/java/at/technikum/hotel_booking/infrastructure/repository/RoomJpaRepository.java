package at.technikum.hotel_booking.infrastructure.repository;

import at.technikum.hotel_booking.infrastructure.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomJpaRepository extends JpaRepository<RoomEntity,Long>{
    
}