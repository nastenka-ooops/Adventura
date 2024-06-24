package com.neotour.controller;

import com.neotour.dto.UserDto;
import com.neotour.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided details and an optional profile picture."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/add")
    public ResponseEntity<UserDto> createUser(
            @Parameter(
                    description = "JSON representation of the user to be created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            )
            @RequestPart(name = "user") @Valid String userDto,
            @Parameter(
                    description = "Optional profile picture for the user",
                    content = @Content(mediaType = "image/jpeg")
            )
            @RequestPart(name = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(userService.createUser(userDto, file));
    }
}
