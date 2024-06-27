package com.neotour.controller;

import com.neotour.dto.LoginRequest;
import com.neotour.dto.LoginResponse;
import com.neotour.dto.RegistrationRequest;
import com.neotour.dto.RegistrationResponse;
import com.neotour.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Login",
            description = "Authenticate user and get access token", tags = {"authentication", "post"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
                    @ApiResponse(responseCode = "401", description = "Invalid username or password", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Not enabled", content = @Content),
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with optional profile picture upload"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful registration", content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "multipart/form-data",
                    schema = @Schema(
                            type = "object",
                            description = "User data in JSON format with not required user photo",
                            properties = {
                                    @StringToClassMapItem(key = "user", value = RegistrationRequest.class),
                                    @StringToClassMapItem(key = "image", value = MultipartFile.class),
                            }
                    )
            )
    )
    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> register(
            @RequestPart(name = "user") String userDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(authenticationService.registration(userDto, image));
    }
}
