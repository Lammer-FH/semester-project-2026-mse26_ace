package at.technikum.hotel_booking.web.dto;

import java.time.LocalDate;

public record BookingResponse(
    Long id,
    Long roomId,
    LocalDate checkIn,
    LocalDate checkout,
    String firstName,
    String lastName,
    String email,
    boolean breakfast
) {}
