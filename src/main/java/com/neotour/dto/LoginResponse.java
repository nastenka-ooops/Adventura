package com.neotour.dto;

public record LoginResponse(
    String username,
    String imageUrl,
    String accessToken
) {
}
