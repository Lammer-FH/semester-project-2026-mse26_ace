package at.technikum.hotelbooking.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.technikum.hotelbooking.domain.model.Booking;
import at.technikum.hotelbooking.infrastructure.mapper.BookingEntityMapper;
import at.technikum.hotelbooking.service.BookingService;
import at.technikum.hotelbooking.web.dto.BookingResponse;
import at.technikum.hotelbooking.web.dto.CreateBookingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Bookings", description = "Create and look up bookings")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Create a new booking",
           description = "Validates dates, checks availability, calculates the total price and saves the booking")
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody CreateBookingRequest request) {
       Booking domain = BookingEntityMapper.toDomain(request);
       Booking saved = bookingService.createBooking(domain);
       BookingResponse response = BookingEntityMapper.toResponse(saved);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a booking by id")
    @GetMapping("/{id}")
    public BookingResponse getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return BookingEntityMapper.toResponse(booking);
    }
    
    
}
