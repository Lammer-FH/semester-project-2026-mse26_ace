package at.technikum.hotelbooking.web.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import at.technikum.hotelbooking.infrastructure.entity.RoomEntity;
import at.technikum.hotelbooking.infrastructure.repository.RoomJpaRepository;

@SpringBootTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.ANY)
class RoomControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    private Long roomId;

    @BeforeEach
    void setUp() {
        roomJpaRepository.deleteAll();

        RoomEntity room = new RoomEntity();
        room.setTitle("Integration Room A");
        room.setDescription("Room A for integration tests");
        room.setPricePerNight(new BigDecimal("120.00"));
        room.setCapacity(2);
        room.setSizeSqm(18);
        RoomEntity saved = roomJpaRepository.save(room);
        roomId = saved.getId();
    }

    @Test
    void getAllRooms_returns200_withListOfRooms() throws Exception {
        mockMvc.perform(get("/api/rooms"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").isNumber())
            .andExpect(jsonPath("$[0].title").value("Integration Room A"));
    }

    @Test
    void getRoomById_returns200_withRightRoom() throws Exception {
        mockMvc.perform(get("/api/rooms/{id}", roomId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(roomId.intValue()))
            .andExpect(jsonPath("$.title").value("Integration Room A"));
    }

    @Test
    void getRoomById_returns404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/rooms/{id}", 99999L))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").exists());
    }

}
