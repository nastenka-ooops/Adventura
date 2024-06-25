package com.neotour.dto;

public record RegistrationResponse(
        String firstName,
        String lastName,
        String email,
        String phone,
        String username,
        String imageUrl
) {
}
