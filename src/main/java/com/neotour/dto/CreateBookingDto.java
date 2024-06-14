package com.neotour.dto;

public record CreateBookingDto(
        String phone,
        int people_amount,
        String comment,
        Long tourId
) {
}
