package at.technikum.hotelbooking.domain.model;

public class RoomImage {
    private final Long id;
    private final String filePath;
    private final int sortOrder;
    private final boolean isPrimary;

    public RoomImage(Long id, String filePath, int sortOrder, boolean isPrimary) {
        this.id = id;
        this.filePath = filePath;
        this.sortOrder = sortOrder;
        this.isPrimary = isPrimary;
    }

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public boolean isPrimary() {
        return isPrimary;
    }
}