package at.technikum.hotelbooking.web.dto;

public record RoomImageResponse(
    Long id,
    String filePath,
    boolean isPrimary
){}