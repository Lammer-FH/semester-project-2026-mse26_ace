package at.technikum.hotel_booking.web.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookingRequest( @NotNull Long roomId,
    @NotNull LocalDate checkIn,
    @NotNull LocalDate checkOut,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank @Email String email,
    boolean breakfast) {
}
