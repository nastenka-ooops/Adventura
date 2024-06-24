package com.neotour.entity;

import com.neotour.enums.Season;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tour")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, name = "booked_amount")
    private int bookedAmount;
    @Column(nullable = false, name = "visited_amount")
    private int visitedAmount;
    @Column(nullable = false, name = "is_featured")
    private Boolean featured;
    @Column(nullable = false, name = "recommended_season")
    @Enumerated(EnumType.ORDINAL)
    private Season recommendedSeason;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "location_id", referencedColumnName = "id")
    private Location location;

    @OneToMany
    @JoinTable(
            name = "tour_image",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;

    @OneToMany(mappedBy = "tour")
    private List<Review> reviews;

    @OneToMany(mappedBy = "tour")
    private List<Booking> bookings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBookedAmount() {
        return bookedAmount;
    }

    public void setBookedAmount(int bookedAmount) {
        this.bookedAmount = bookedAmount;
    }

    public int getVisitedAmount() {
        return visitedAmount;
    }

    public void setVisitedAmount(int visited_amount) {
        this.visitedAmount = visited_amount;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Season getRecommendedSeason() {
        return recommendedSeason;
    }

    public void setRecommendedSeason(Season recommendedSeason) {
        this.recommendedSeason = recommendedSeason;
    }
}
