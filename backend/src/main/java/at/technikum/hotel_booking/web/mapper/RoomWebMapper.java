package at.technikum.hotel_booking.web.mapper;

import at.technikum.hotel_booking.domain.model.*;
import at.technikum.hotel_booking.web.dto.*;
import java.util.ArrayList;
import java.util.List;

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
            room.getId,
            room.getTitle,
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
            image.isPriamry()
        );
    }

    private static ExtraResponse toResponse(Extra extra){
        return new ExtraResponse(
            extra.getId(),
            extra.getName(),
            extra.getIconName()
        );
    }
}