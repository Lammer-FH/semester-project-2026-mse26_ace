package at.technikum.hotel_booking.domain.model;

import java.time.LocalDate;

public class Booking {
    private final Long id;
    private final Long roomId;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final boolean breakfast;

    public Booking(Long id, Long roomId, LocalDate checkIn, LocalDate checkOut, String firstName, String lastName,
            String email, boolean breakfast) {
        this.id = id;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.breakfast = breakfast;
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

    
}
