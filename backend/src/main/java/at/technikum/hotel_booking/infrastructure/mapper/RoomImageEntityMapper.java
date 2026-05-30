package at.technikum.hotel_booking.infrastructure.mapper;

import at.technikum.hotel_booking.domain.model.RoomImage;
import at.technikum.hotel_booking.infrastructure.entity.RoomImageEntity;

public final class RoomImageEntityMapper {
    private RoomImageEntityMapper() {
        // Private constructor to prevent instantiation
    }
    public static RoomImage toDomain(RoomImageEntity roomImageEntity) {
        if (roomImageEntity == null) {
            return null;
        }
        return new RoomImage(
                roomImageEntity.getId(),
                roomImageEntity.getFilePath(),
                roomImageEntity.getSortOrder(),
                roomImageEntity.isPrimary()
        );
    }
}
