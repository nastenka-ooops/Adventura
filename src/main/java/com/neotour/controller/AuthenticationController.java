package com.neotour.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neotour.dto.*;
import com.neotour.service.AuthenticationService;
import com.neotour.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;

    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> register(@RequestPart("user") String userDto,
                                            @RequestPart(value = "file", required = false) MultipartFile file) {

        return ResponseEntity.ok(userService.createUser(userDto, file));
    }


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}
