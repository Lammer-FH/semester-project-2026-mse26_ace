package at.technikum.hotel_booking.web.controller;

import at.technikum.hotel_booking.domain.model.Availability;
import at.technikum.hotel_booking.domain.model.BookingPeriod;
import at.technikum.hotel_booking.service.AvailabilityService;
import at.technikum.hotel_booking.service.RoomService;
import at.technikum.hotel_booking.web.dto.AvailabilityResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/rooms")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final RoomService roomService;

    public AvailabilityController(
            AvailabilityService availabilityService,
            RoomService roomService
    ) {
        this.availabilityService = availabilityService;
        this.roomService = roomService;
    }

    @GetMapping("/{roomId}/availability")
    public AvailabilityResponse checkAvailability(
            @PathVariable Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut
    ) {
        validateBookingPeriod(checkIn, checkOut);

        // Wichtig: zuerst prüfen, ob das Zimmer überhaupt existiert.
        // Wenn nicht, wirft RoomService bereits RoomNotFoundException.
        roomService.getRoomById(roomId);

        BookingPeriod period = new BookingPeriod(checkIn, checkOut);

        Availability availability = availabilityService.checkAvailability(period)
                .stream()
                .filter(item -> item.getRoomId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new RoomAvailabilityNotFoundException(roomId));

        return new AvailabilityResponse(
                availability.getRoomId(),
                availability.getPeriod().getCheckInDate(),
                availability.getPeriod().getCheckOutDate(),
                availability.isAvailable()
        );
    }

    private void validateBookingPeriod(LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidBookingPeriodException(
                    "Check-out date must be after check-in date."
            );
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private static class InvalidBookingPeriodException extends RuntimeException {
        public InvalidBookingPeriodException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class RoomAvailabilityNotFoundException extends RuntimeException {
        public RoomAvailabilityNotFoundException(Long roomId) {
            super("Availability for room not found: " + roomId);
        }
    }
}