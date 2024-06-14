package com.neotour.dto;

import java.util.List;

public record TourDto(
        Long id,
        String name,
        String description,
        String location,
        String country,
        List<String> images,
        List<ReviewDto> reviews
) {
}
