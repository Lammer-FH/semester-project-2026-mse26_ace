package at.technikum.hotel_booking.web.dto;

public record RoomImageResponse(
    Long id,
    String filePath,
    boolean isPriamry
){}