package at.technikum.hotel_booking.infrastructure.mapper;

import at.technikum.hotel_booking.domain.model.Extra;

public final class ExtraEntityMapper {
    private ExtraEntityMapper() {
        // Private constructor to prevent instantiation
    }
    
    public static Extra toDomain(at.technikum.hotel_booking.infrastructure.entity.ExtraEntity extraEntity) {
        if (extraEntity == null) {
            return null;
        }
        return new Extra(
                extraEntity.getId(),
                extraEntity.getName(),
                extraEntity.getDescription(),
                extraEntity.getIconName()
        );
    }
}
