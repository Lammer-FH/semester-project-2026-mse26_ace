package at.technikum.hotel_booking.web.dto;

import java.time.LocalDate;

public record AvailabilityResponse(
        Long roomId,
        LocalDate checkIn,
        LocalDate checkOut,
        boolean available
) {
}