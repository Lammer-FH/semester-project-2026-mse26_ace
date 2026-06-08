package at.technikum.hotelbooking.web.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import at.technikum.hotelbooking.infrastructure.entity.RoomEntity;
import at.technikum.hotelbooking.infrastructure.repository.RoomJpaRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    private Long roomId;

    @BeforeEach
    void setUp() {
        roomJpaRepository.deleteAll();
        RoomEntity room = new RoomEntity();
        room.setTitle("Integration Test Room");
        room.setDescription("A room for testing");
        room.setPricePerNight(new BigDecimal("100.00"));
        room.setCapacity(2);
        room.setSizeSqm(20);
        RoomEntity saved = roomJpaRepository.save(room);
        roomId = saved.getId();
    }

    @Test
    void postBooking_shouldReturn201_whenValidRequest() throws Exception {
        String body = """
            {
                "roomId": %d,
                "checkIn": "2026-12-01",
                "checkOut": "2026-12-05",
                "firstName": "Integration",
                "lastName": "Test",
                "email": "integration@example.com",
                "breakfast": false
            }
            """.formatted(roomId);

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.priceAtBooking").value(400.00))
            .andExpect(jsonPath("$.firstName").value("Integration"));
    }
}
