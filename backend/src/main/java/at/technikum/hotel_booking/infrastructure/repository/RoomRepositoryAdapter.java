package at.technikum.hotel_booking.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import at.technikum.hotel_booking.domain.model.Room;
import at.technikum.hotel_booking.domain.port.RoomRepository;
import at.technikum.hotel_booking.infrastructure.entity.RoomEntity;
import at.technikum.hotel_booking.infrastructure.mapper.RoomEntityMapper;

@Component
public class RoomRepositoryAdapter implements RoomRepository{
    private final RoomJpaRepository jpa;

    public RoomRepositoryAdapter(RoomJpaRepository jpa){
        this.jpa=jpa;
    }

    @Override
    public List<Room> findAll(){
        List <Room> rooms= new ArrayList<>();
        for (RoomEntity entity : jpa.findAll()) {
            rooms.add(RoomEntityMapper.toDomain(entity));
        }
        return rooms;
    }

    @Override
    public Optional<Room> findById(Long id){
        return jpa.findById(id).map(RoomEntityMapper::toDomain);
    }
}
