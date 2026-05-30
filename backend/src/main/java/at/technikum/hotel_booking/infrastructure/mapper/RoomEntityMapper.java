//translator for RoomEntity to Room (what the service layer uses) and vice versa
package at.technikum.hotel_booking.infrastructure.mapper;

import at.technikum.hotel_booking.domain.model.Room;
import at.technikum.hotel_booking.infrastructure.entity.RoomEntity;


public final class RoomEntityMapper {
   private RoomEntityMapper() {
        // Private constructor to prevent instantiation
    }
   
    public static Room toDomain(RoomEntity roomEntity) {
        if (roomEntity == null) {
            return null;
        }
        return new Room(
                roomEntity.getId(),
                roomEntity.getTitle(),
                roomEntity.getDescription(),
                roomEntity.getPricePerNight(),
                roomEntity.getCapacity(),
                roomEntity.getSizeSqm(),
                roomEntity.getImages().stream().map(RoomImageEntityMapper::toDomain).toList(),
                roomEntity.getExtrasSet().stream().map(ExtraEntityMapper::toDomain).toList()
        );
    }
}
