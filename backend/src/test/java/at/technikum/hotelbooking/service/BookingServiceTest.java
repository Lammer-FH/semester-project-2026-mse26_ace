package at.technikum.hotelbooking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import at.technikum.hotelbooking.domain.model.Booking;
import at.technikum.hotelbooking.domain.model.BookingPeriod;
import at.technikum.hotelbooking.domain.model.Room;
import at.technikum.hotelbooking.domain.port.BookingRepository;
import at.technikum.hotelbooking.domain.port.RoomRepository;

@ExtendWith(MockitoExtension.class) //for @mock annotation to create a fake object
public class BookingServiceTest {
    
    @Mock
    private BookingRepository bookingRepository; 

    @Mock
    private RoomRepository roomRepository;
    
    private BookingService bookingService;

    @BeforeEach
    void setUp(){
        bookingService = new BookingService(bookingRepository, roomRepository);
    }

    @Test
    void createBooking_shouldThrow_whenCheckOutIsBeforeCheckIn(){
        Booking invalidBooking = new Booking(
            null, 
            1L, 
            LocalDate.of(2026,10,5), 
            LocalDate.of(2026,10,3), 
            "Max", 
            "Mustermann", 
            "mm@test.com", 
            false, 
            null, 
            null
        );

        assertThatThrownBy(()-> bookingService.createBooking(invalidBooking))
            .isInstanceOf(InvalidBookingException.class)
            .hasMessageContaining("Check out must be after check in");
    }

    @Test
    void createBooking_shouldThrow_whenRoomisNotAvailable(){
        Booking validDates = new Booking(
            null, 
            1L, 
            LocalDate.of(2026,10,1), 
            LocalDate.of(2026,10,5), 
            "Max", 
            "Mustermann", 
            "mm@test.com", 
            false, 
            null, 
            null
        );

        BookingPeriod existingBooking= new BookingPeriod(LocalDate.of(2026,10,2), LocalDate.of(2026,10,4));

        when(bookingRepository.findOverlappingBookings(anyLong(), any(), any()))
            .thenReturn(List.of(existingBooking));

        assertThatThrownBy(()-> bookingService.createBooking(validDates))
            .isInstanceOf(RoomNotAvailableException.class)
            .hasMessageContaining("Room is not available");
    }

    @Test
    void createBooking_shouldCalculatePriceAndSave_whenValid() {
        Booking incomingBooking = new Booking(
            null,
            1L,
            LocalDate.of(2026, 10, 1),
            LocalDate.of(2026, 10, 5),
            "Test",
            "User",
            "test@example.com",
            false,
            null,
            null
        );

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

        Booking savedBooking = new Booking(
            42L,
            1L,
            LocalDate.of(2026, 10, 1),
            LocalDate.of(2026, 10, 5),
            "Test",
            "User",
            "test@example.com",
            false,
            new BigDecimal("400.00"),
            null
        );

        when(bookingRepository.findOverlappingBookings(anyLong(), any(), any()))
            .thenReturn(Collections.emptyList());
        when(roomRepository.findById(1L))
            .thenReturn(Optional.of(mockedRoom));
        when(bookingRepository.save(any(Booking.class)))
            .thenReturn(savedBooking);

        Booking result = bookingService.createBooking(incomingBooking);

        assertThat(result.getId()).isEqualTo(42L);
        assertThat(result.getPriceAtBooking()).isEqualByComparingTo("400.00");
        assertThat(result.getFirstName()).isEqualTo("Test");
        
        verify(bookingRepository).save(any(Booking.class));
    }

}
