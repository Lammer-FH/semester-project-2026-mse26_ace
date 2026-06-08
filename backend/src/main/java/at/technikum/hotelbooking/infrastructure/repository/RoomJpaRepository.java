package at.technikum.hotelbooking.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import at.technikum.hotelbooking.infrastructure.entity.RoomEntity;

public interface RoomJpaRepository extends JpaRepository<RoomEntity,Long>{
    
}