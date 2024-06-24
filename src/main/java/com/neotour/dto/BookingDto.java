package com.neotour.dto;

public record BookingDto(
        Long id,
        String phone,
        String comment,
        int peopleAmount,
        String username,
        Long tourId
) {
}
