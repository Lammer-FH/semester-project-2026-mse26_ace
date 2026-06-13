package at.technikum.hotelbooking.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.technikum.hotelbooking.domain.model.Room;
import at.technikum.hotelbooking.service.RoomService;
import at.technikum.hotelbooking.web.dto.RoomResponse;
import at.technikum.hotelbooking.web.mapper.RoomWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Rooms", description = "Get information about rooms")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;
    public RoomController (RoomService roomService){
        this.roomService=roomService;
    }

    @Operation(summary = "Get all rooms", description = "Returns a list of all rooms with their details, images and extras")    
    @GetMapping
    public List<RoomResponse> getAllRooms(){
        return roomService.getRooms().stream()
            .map(RoomWebMapper::toResponse)
            .toList();
    }

    @Operation(summary = "Get a room by id", description = "Returns the details, images and extras of a specific room by its id")   
    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable Long id){
        Room room = roomService.getRoomById(id);
        return RoomWebMapper.toResponse(room);
    }

}
