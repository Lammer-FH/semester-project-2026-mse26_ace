package at.technikum.hotel_booking.domain.model;

import java.math.BigDecimal;
import java.util.List;

public class Room{
    private final Long id;
    private final String title;
    private final String description;
    private final BigDecimal pricePerNight;
    private final int capacity;
    private final int sizeSqm;
    private final List<RoomImage> images;
    private final List<Extra> extras;

    public Room(Long id, String title, String description, BigDecimal pricePerNight, int capacity, int sizeSqm, List<RoomImage> images, List<Extra> extras) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.sizeSqm = sizeSqm;
        this.images = images;
        this.extras = extras;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSizeSqm() {
        return sizeSqm;
    }

    public List<RoomImage> getImages() {
        return images;
    }

    public List<Extra> getExtras() {
        return extras;
    }
}