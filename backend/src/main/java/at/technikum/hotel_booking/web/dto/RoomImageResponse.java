package at.technikum.hotel_booking.web.dto;

public record RoomImageResponse(
    Long id,
    String url,
    boolean isMainImage
){}