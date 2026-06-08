package at.technikum.hotelbooking.web.mapper;

import java.util.ArrayList;
import java.util.List;

import at.technikum.hotelbooking.domain.model.Extra;
import at.technikum.hotelbooking.domain.model.Room;
import at.technikum.hotelbooking.domain.model.RoomImage;
import at.technikum.hotelbooking.web.dto.ExtraResponse;
import at.technikum.hotelbooking.web.dto.RoomImageResponse;
import at.technikum.hotelbooking.web.dto.RoomResponse;

public final class RoomWebMapper{
    private RoomWebMapper(){

    }
    
    public static RoomResponse toResponse(Room room){
        List<RoomImageResponse> images = new ArrayList<>();
        for(RoomImage image : room.getImages()){
            images.add(toResponse(image));
        }

        List<ExtraResponse> extras = new ArrayList<>();
        for(Extra extra : room.getExtras()){
            extras.add(toResponse(extra));
        }

        return new RoomResponse(
            room.getId(),
            room.getTitle(),
            room.getDescription(),
            room.getPricePerNight(),
            room.getCapacity(),
            room.getSizeSqm(),
            images,
            extras
        );
    }

    private static RoomImageResponse toResponse(RoomImage image){
        return new RoomImageResponse(
            image.getId(),
            image.getFilePath(),
            image.isPrimary()
        );
    }

    private static ExtraResponse toResponse(Extra extra){
        return new ExtraResponse(
            extra.getId(),
            extra.getName(),
            extra.getIconName(),
            extra.getDescription()
        );
    }
}