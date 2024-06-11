package com.neotour.dto;

public record ReviewDto(
        Long id,
        String review,
        TourDto tourDto,
        UserDto userDto
) {
}
