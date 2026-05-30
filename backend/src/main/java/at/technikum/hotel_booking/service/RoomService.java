package at.technikum.hotel_booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import at.technikum.hotel_booking.domain.model.Room;
import at.technikum.hotel_booking.domain.port.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("room not found: " + id));
    }

}