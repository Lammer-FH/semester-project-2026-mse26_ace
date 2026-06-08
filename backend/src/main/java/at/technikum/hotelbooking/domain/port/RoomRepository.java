package at.technikum.hotelbooking.domain.port;
import java.util.List;
import java.util.Optional;

import at.technikum.hotelbooking.domain.model.Room;

public interface RoomRepository {
    List<Room> findAll();
    Optional<Room> findById(Long id);
}