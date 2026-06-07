package at.technikum.hotel_booking.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingResponse(
    Long id,
    Long roomId,
    LocalDate checkIn,
    LocalDate checkout,
    String firstName,
    String lastName,
    String email,
    boolean breakfast,
    BigDecimal priceAtBooking,
    LocalDateTime createdAt
) {}
