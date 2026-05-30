package at.technikum.hotel_booking.domain.model;

public class Extra {
    private final Long id;
    private final String name;
    private final String description;
    private final String iconName;

    public Extra(Long id, String name, String description, String iconName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconName = iconName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconName() {
        return iconName;
    }

    public String getDescription() {
        return description;
    }

}