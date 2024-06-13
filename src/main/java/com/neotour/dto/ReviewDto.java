package com.neotour.dto;

public record ReviewDto(
        String review,
        String username,
        String imageUrl,
        Long tourId
) {
}
