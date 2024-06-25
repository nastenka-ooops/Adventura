package com.neotour.dto;

import java.security.SecureRandom;

public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String phone,
        String username,
        String password,
        String confirmPassword
) {
}
