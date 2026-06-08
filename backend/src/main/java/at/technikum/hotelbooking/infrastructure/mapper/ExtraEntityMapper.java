package at.technikum.hotelbooking.infrastructure.mapper;

import at.technikum.hotelbooking.domain.model.Extra;

public final class ExtraEntityMapper {
    private ExtraEntityMapper() {
        // Private constructor to prevent instantiation
    }
    
    public static Extra toDomain(at.technikum.hotelbooking.infrastructure.entity.ExtraEntity extraEntity) {
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
