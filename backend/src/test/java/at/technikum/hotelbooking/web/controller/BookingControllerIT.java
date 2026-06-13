package at.technikum.hotelbooking.web.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import at.technikum.hotelbooking.infrastructure.entity.RoomEntity;
import at.technikum.hotelbooking.infrastructure.repository.BookingJpaRepository;
import at.technikum.hotelbooking.infrastructure.repository.RoomJpaRepository;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    private BookingJpaRepository bookingJpaRepository;

    private Long roomId;

    @BeforeEach
    void setUp() {
        bookingJpaRepository.deleteAll();
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

    @Test
    void getBookingById_returns200_andBooking_whenExists() throws Exception {
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

        var mvcResult = mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber())
            .andReturn();

        String resp = mvcResult.getResponse().getContentAsString();
        com.fasterxml.jackson.databind.JsonNode node = new com.fasterxml.jackson.databind.ObjectMapper().readTree(resp);
        long id = node.get("id").asLong();

        mockMvc.perform(get("/api/bookings/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value((int) id))
            .andExpect(jsonPath("$.firstName").value("Integration"));
    }

    @Test
    void getBookingById_returns404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/bookings/{id}", 99999L))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").exists());
    }
}
