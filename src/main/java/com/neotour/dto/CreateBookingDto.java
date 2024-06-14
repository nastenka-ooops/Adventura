package com.neotour.dto;

import jakarta.validation.constraints.*;

public record CreateBookingDto(
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid and contain 10 to 15 digits")
        String phone,

        @Min(value = 1, message = "People amount must be at least 1")
        @Max(value = 50, message = "People amount must be less than or equal to 50")
        int people_amount,

        @Size(max = 500, message = "Comment cannot be longer than 500 characters")
        String comment,

        @NotNull(message = "Tour ID must not be null")
        Long tourId
) {
}
