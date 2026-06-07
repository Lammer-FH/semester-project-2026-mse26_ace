package at.technikum.hotel_booking.domain.model;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private final Long id;
    private final Long roomId;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final boolean breakfast;
    private final BigDecimal priceAtBooking;
    private final LocalDateTime createdAt;

    public Booking(Long id, Long roomId, LocalDate checkIn, LocalDate checkOut, String firstName, String lastName,
            String email, boolean breakfast,BigDecimal priceAtBooking, LocalDateTime createdAt) {
        this.id = id;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.breakfast = breakfast;
        this.priceAtBooking= priceAtBooking;
        this.createdAt= createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBreakfast() {
        return breakfast;
    }

    public BigDecimal getPriceAtBooking(){
        return priceAtBooking;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    
}
