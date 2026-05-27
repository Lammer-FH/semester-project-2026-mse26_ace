package at.technikum.hotel_booking.domain.port;
import java.util.List;
import java.util.Optional;

import at.technikum.hotel_booking.domain.model.BookingPeriod;
import at.technikum.hotel_booking.domain.model.Extra;
import at.technikum.hotel_booking.domain.model.Room;
import at.technikum.hotel_booking.domain.model.RoomImage;

public interface RoomRepository {
    List<Room> findAll();
    Optional<Room> findById(Long id);
    List<Room> findAvailableRooms(BookingPeriod period);
    List<Extra> findExtrasForRoom(Long roomId);
    List<RoomImage> findImagesForRoom(Long roomId);
}