package at.technikum.hotel_booking.service;
import at.technikum.hotel_booking.domain.model.BookingPeriod;
import at.technikum.hotel_booking.domain.model.Room;
import at.technikum.hotel_booking.domain.model.Availability;
import at.technikum.hotel_booking.domain.port.BookingRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {

    private final RoomService roomService;
    private final BookingRepository bookingRepository;

    public AvailabilityService(RoomService roomService, BookingRepository bookingRepository) {
        this.roomService = roomService;
        this.bookingRepository = bookingRepository;
    }

    public List<Availability> checkAvailability(BookingPeriod period) {
        List<Room> rooms = roomService.getRooms();
        List<Availability> availabilities = new ArrayList<>();

        for (Room room : rooms) {
            boolean isAvailable = bookingRepository.findOverlappingBookings(
                room.getId(), period.getCheckInDate(), period.getCheckOutDate()
            ).isEmpty();
            BigDecimal totalPrice = isAvailable ? room.getPricePerNight().multiply(BigDecimal.valueOf(period.getNumberOfNights())) : BigDecimal.ZERO;
            availabilities.add(new Availability(room.getId(), period, isAvailable, totalPrice));
        }

        return availabilities;
    }
}