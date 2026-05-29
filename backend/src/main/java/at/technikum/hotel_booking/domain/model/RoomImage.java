package at.technikum.hotel_booking.domain.model;

public class RoomImage {
    private final Long id;
    private final String url;
    private final int order;
    private final boolean isMainImage;

    public RoomImage(Long id, String url, int order, boolean isMainImage) {
        this.id = id;
        this.url = url;
        this.order = order;
        this.isMainImage = isMainImage;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getOrder() {
        return order;
    }

    public boolean isMainImage() {
        return isMainImage;
    }
}