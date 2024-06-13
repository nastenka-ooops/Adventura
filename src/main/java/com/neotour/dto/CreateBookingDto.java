package com.neotour.dto;

public record CreateBookingDto(
        String phone,
        String comment,
        Long tourId
) {
}
