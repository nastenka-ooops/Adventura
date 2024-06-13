package com.neotour.dto;

import java.util.List;

public record UserDto(
        Long id,
        String username,
        String password
        //List<ImageDto> images
){}
