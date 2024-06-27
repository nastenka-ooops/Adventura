package com.neotour.entity;

import com.neotour.enums.Season;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tour")
@Data
@NoArgsConstructor
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

}
