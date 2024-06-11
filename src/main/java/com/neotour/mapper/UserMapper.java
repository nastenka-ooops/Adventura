package com.neotour.mapper;

import com.neotour.dto.UserDto;
import com.neotour.entity.User;

public class UserMapper {
    public static UserDto mapUserToUserDto(User user) {
        return new UserDto();
    }
}
