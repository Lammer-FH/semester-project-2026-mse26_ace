package at.technikum.hotel_booking.infrastructure.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price_per_night", nullable = false)
    private BigDecimal pricePerNight;
    @Column(name = "capacity", nullable = false)
    private int capacity;
    @Column(name = "size_sqm", nullable = false)
    private int sizeSqm;    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImageEntity> images = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "room_extras",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "extras_id")
    )
    private Set<ExtraEntity> extrasSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSizeSqm() {
        return sizeSqm;
    }

    public void setSizeSqm(int sizeSqm) {
        this.sizeSqm = sizeSqm;
    }

    public List<RoomImageEntity> getImages() {
        return images;
    }

    public void setImages(List<RoomImageEntity> images) {
        this.images = images;
    }

    public Set<ExtraEntity> getExtrasSet() {
        return extrasSet;
    }

    public void setExtrasSet(Set<ExtraEntity> extrasSet) {
        this.extrasSet = extrasSet;
    }
}
