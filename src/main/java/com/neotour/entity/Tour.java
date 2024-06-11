package com.neotour.entity;

import jakarta.persistence.*;

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
    //TODO add Location and Image

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
}
