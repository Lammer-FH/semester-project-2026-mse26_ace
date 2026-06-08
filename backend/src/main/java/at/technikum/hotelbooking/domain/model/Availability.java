package at.technikum.hotelbooking.domain.model;

import java.math.BigDecimal;

public class Availability {
    private final Long roomId;
    private final BookingPeriod period;
    private final boolean isAvailable;
    private final BigDecimal totalPrice;

    public Availability(Long roomId, BookingPeriod period, boolean isAvailable, BigDecimal totalPrice) {
        this.roomId = roomId;
        this.period = period;
        this.isAvailable = isAvailable;
        this.totalPrice = totalPrice;
    }

    public Long getRoomId() {
        return roomId;
    }

    public BookingPeriod getPeriod() {
        return period;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
}