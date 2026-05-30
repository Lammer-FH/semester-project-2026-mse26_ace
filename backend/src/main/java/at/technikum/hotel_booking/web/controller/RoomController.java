package at.technikum.hotel_booking.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.technikum.hotel_booking.domain.model.Room;
import at.technikum.hotel_booking.service.RoomService;
import at.technikum.hotel_booking.web.dto.RoomResponse;
import at.technikum.hotel_booking.web.mapper.RoomWebMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(origins = "http://localhost:8100")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;
    public RoomController (RoomService roomService){
        this.roomService=roomService;
    }

    @GetMapping
    public List<RoomResponse> getAllRooms(){
        List<RoomResponse> result = new ArrayList<>();
        for(Room room : roomService.getRooms()){
            result.add(RoomWebMapper.toResponse(room));
        }
        return result;
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable Long id){
        Room room = roomService.getRoomById(id);
        return RoomWebMapper.toResponse(room);
    }

}
