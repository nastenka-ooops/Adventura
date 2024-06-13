package com.neotour.dto;

public record BookingDto(
    Long id,
    String phone,
    String comment,
    String username,
    Long tourId
){}
