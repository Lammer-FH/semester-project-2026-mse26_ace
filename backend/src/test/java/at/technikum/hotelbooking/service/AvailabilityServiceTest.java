package at.technikum.hotelbooking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import at.technikum.hotelbooking.domain.model.Availability;
import at.technikum.hotelbooking.domain.model.BookingPeriod;
import at.technikum.hotelbooking.domain.model.Room;
import at.technikum.hotelbooking.domain.port.BookingRepository;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    private RoomService roomService;

    @Mock
    private BookingRepository bookingRepository;

    private AvailabilityService availabilityService;

    @BeforeEach
    void setUp() {
        availabilityService = new AvailabilityService(roomService, bookingRepository);
    }

    @Test
    void checkAvailability_marksRoomAvailable_andCalculatesTotalPrice_whenNoOverlaps() {
        BookingPeriod period = new BookingPeriod(LocalDate.of(2026, 6, 10), LocalDate.of(2026, 6, 13)); // 3 nights

        Room room = new Room(1L, "R", "r", new BigDecimal("100.00"), 2, 20, Collections.emptyList(), Collections.emptyList());

        when(roomService.getRooms()).thenReturn(List.of(room));
        when(bookingRepository.findOverlappingBookings(1L, period.getCheckInDate(), period.getCheckOutDate()))
            .thenReturn(Collections.emptyList());

        List<Availability> availabilities = availabilityService.checkAvailability(period);

        assertThat(availabilities).hasSize(1);
        Availability a = availabilities.get(0);
        assertThat(a.getRoomId()).isEqualTo(1L);
        assertThat(a.isAvailable()).isTrue();
        assertThat(a.getTotalPrice()).isEqualByComparingTo(new BigDecimal("100.00").multiply(BigDecimal.valueOf(period.getNumberOfNights())));
    }

    @Test
    void checkAvailability_marksRoomNotAvailable_andTotalPriceZero_whenOverlapsExist() {
        BookingPeriod period = new BookingPeriod(LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 4)); // 3 nights

        Room room = new Room(2L, "R2", "r2", new BigDecimal("80.00"), 2, 18, Collections.emptyList(), Collections.emptyList());

        when(roomService.getRooms()).thenReturn(List.of(room));
        when(bookingRepository.findOverlappingBookings(2L, period.getCheckInDate(), period.getCheckOutDate()))
            .thenReturn(List.of(new BookingPeriod(LocalDate.of(2026, 7, 2), LocalDate.of(2026, 7, 3))));

        List<Availability> availabilities = availabilityService.checkAvailability(period);

        assertThat(availabilities).hasSize(1);
        Availability a = availabilities.get(0);
        assertThat(a.getRoomId()).isEqualTo(2L);
        assertThat(a.isAvailable()).isFalse();
        assertThat(a.getTotalPrice()).isEqualByComparingTo(BigDecimal.ZERO);
    }

}
