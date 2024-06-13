package com.neotour.mapper;

import com.neotour.dto.UserDto;
import com.neotour.entity.RoleEnum;
import com.neotour.entity.AppUser;

import java.util.ArrayList;
import java.util.HashSet;

public class UserMapper {
    public static UserDto mapToUserDto(AppUser user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword()
                //user.getImages().stream().map(ImageMapper::mapToImageDto).toList()
        );
    }

    public static AppUser mapToUserEntity(UserDto userDto) {
        return new AppUser(userDto.username(), userDto.password(), new HashSet<>(RoleEnum.USER.ordinal()),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}
