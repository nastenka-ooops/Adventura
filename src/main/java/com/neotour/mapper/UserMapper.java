package com.neotour.mapper;

import com.neotour.dto.UserDto;
import com.neotour.entity.AppUser;

public class UserMapper {
    public static UserDto mapToUserDto(AppUser user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(),
                user.getImages().stream().map(ImageMapper::mapToImageDto).toList());
    }

    /*public static AppUser mapToUserEntity(UserDto userDto) {
        AppUser appUser = new AppUser(
                userDto.getUsername(),
                userDto.getPassword(),
                //userDto.getRoles() != null ? new HashSet<>(userDto.getRoles()) : new HashSet<>(),
                userDto.getImages() != null ? new ArrayList<>(userDto.getImages()) : new ArrayList<>(),
                //userDto.getBookings() != null ? new ArrayList<>(userDto.getBookings()) : new ArrayList<>(),
                //userDto.getReviews() != null ? new ArrayList<>(userDto.getReviews()) : new ArrayList<>()
        );

        return appUser;
    }*/
}
