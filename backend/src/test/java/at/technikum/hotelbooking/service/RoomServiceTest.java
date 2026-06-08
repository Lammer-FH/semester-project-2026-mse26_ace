package at.technikum.hotelbooking.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import at.technikum.hotelbooking.domain.model.Room;
import at.technikum.hotelbooking.domain.port.RoomRepository;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    private RoomService roomService;

    @BeforeEach
    void setUp() {
        roomService = new RoomService(roomRepository);
    }

    @Test
    void getRoomById_returnsRoom_whenExists() {
        Room mockedRoom = new Room(
            1L,
            "Test Room",
            "Description",
            new BigDecimal("100.00"),
            2,
            20,
            Collections.emptyList(),
            Collections.emptyList()
        );

        when(roomRepository.findById(1L)).thenReturn(Optional.of(mockedRoom));

        Room result = roomService.getRoomById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Room");
    }

    @Test
    void getRoomById_throwsRoomNotFoundException_whenNotFound() {
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roomService.getRoomById(99L))
            .isInstanceOf(RoomNotFoundException.class)
            .hasMessageContaining("room not found");
    }

    @Test
    void getRooms_returnsAllRooms() {
        Room r1 = new Room(1L, "A", "a", new BigDecimal("50.00"), 1, 10, Collections.emptyList(), Collections.emptyList());
        Room r2 = new Room(2L, "B", "b", new BigDecimal("75.00"), 2, 15, Collections.emptyList(), Collections.emptyList());

        when(roomRepository.findAll()).thenReturn(List.of(r1, r2));

        List<Room> result = roomService.getRooms();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }

}
