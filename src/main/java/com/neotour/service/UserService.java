package com.neotour.service;

import com.neotour.dto.UserDto;
import com.neotour.entity.AppUser;
import com.neotour.mapper.UserMapper;
import com.neotour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDto) {
        AppUser user = UserMapper.mapToUserEntity(userDto);
        AppUser savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }
}
