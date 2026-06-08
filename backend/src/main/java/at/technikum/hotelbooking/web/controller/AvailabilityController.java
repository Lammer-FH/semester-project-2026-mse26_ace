package at.technikum.hotelbooking.web.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import at.technikum.hotelbooking.domain.model.Availability;
import at.technikum.hotelbooking.domain.model.BookingPeriod;
import at.technikum.hotelbooking.service.AvailabilityService;
import at.technikum.hotelbooking.service.RoomService;
import at.technikum.hotelbooking.web.dto.AvailabilityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Availability", description = "Check room availability for a given period")
@RestController
@RequestMapping("/api/rooms/availability")
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

    @Operation(summary = "Check availability of a room for a given period",
               description = "Returns whether the room is available and the total price for the period if it is available")
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