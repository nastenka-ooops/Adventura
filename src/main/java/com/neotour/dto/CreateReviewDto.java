package com.neotour.dto;

public record CreateReviewDto(
        String review,
        Long tourId
) {
}
