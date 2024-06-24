package com.neotour.dto;

public record LoginRequest(
        String username,
        String password
) {
}
