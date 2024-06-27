package com.neotour.entity;

import com.neotour.enums.Continent;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "location", indexes = {
        @Index(name = "location_name_pk", columnList = "location"),
        @Index(name = "location_continent_pk", columnList = "continent")})
@Data
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Continent continent;

    @OneToMany(mappedBy = "location")
    private List<Tour> tours;

    public Location(String location, String country, Continent continent) {
        this.location = location;
        this.country = country;
        this.continent = continent;
    }
}
