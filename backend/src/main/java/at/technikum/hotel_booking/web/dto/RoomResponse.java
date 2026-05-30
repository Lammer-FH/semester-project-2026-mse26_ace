package at.technikum.hotel_booking.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record RoomResponse(
    Long id,
    String title,
    String description,
    BigDecimal pricePerNight,
    int capacity,
    int sizeSqm,
    List<RoomImageResponse> images,
    List<ExtraResponse> extras
){}