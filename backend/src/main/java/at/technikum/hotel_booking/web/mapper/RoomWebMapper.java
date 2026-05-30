package at.technikum.hotel_booking.web.mapper;

import java.util.ArrayList;
import java.util.List;

import at.technikum.hotel_booking.domain.model.Extra;
import at.technikum.hotel_booking.domain.model.Room;
import at.technikum.hotel_booking.domain.model.RoomImage;
import at.technikum.hotel_booking.web.dto.ExtraResponse;
import at.technikum.hotel_booking.web.dto.RoomImageResponse;
import at.technikum.hotel_booking.web.dto.RoomResponse;

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
            image.getUrl(),
            image.isMainImage()
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