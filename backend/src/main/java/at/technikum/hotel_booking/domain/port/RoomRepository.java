package at.technikum.hotel_booking.domain.port;
import java.util.List;
import java.util.Optional;

import at.technikum.hotel_booking.domain.model.Room;

public interface RoomRepository {
    List<Room> findAll();
    Optional<Room> findById(Long id);
}