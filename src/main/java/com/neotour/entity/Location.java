package com.neotour.entity;

import com.neotour.enums.Continent;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "location")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }
}
