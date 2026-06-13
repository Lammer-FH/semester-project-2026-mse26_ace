package at.technikum.hotelbooking.domain.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class BookingPeriod {
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;

    public BookingPeriod(LocalDate checkInDate, LocalDate checkOutDate) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    public boolean overlapsWith(BookingPeriod other) {
        return !(this.checkOutDate.isEqual(other.checkInDate) || this.checkOutDate.isBefore(other.checkInDate) ||
                 this.checkInDate.isEqual(other.checkOutDate) || this.checkInDate.isAfter(other.checkOutDate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingPeriod that = (BookingPeriod) o;
        return Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkInDate, checkOutDate);
    }
}