package com.neotour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neotour.dto.UserDto;
import com.neotour.entity.AppUser;
import com.neotour.entity.Role;
import com.neotour.enums.RoleEnum;
import com.neotour.error.ImageUploadException;
import com.neotour.error.UserCreationException;
import com.neotour.mapper.UserMapper;
import com.neotour.repository.RoleRepository;
import com.neotour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final ImageService imageService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public UserService(UserRepository userRepository,
                       ImageService imageService,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.roleRepository = roleRepository;
    }

    public UserDto createUser(String dto, MultipartFile file) {
        UserDto userDto;

        try {
            userDto = objectMapper.readValue(dto, UserDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON format for UserDto");
        }
        AppUser user = new AppUser(userDto.getUsername(),
                                   bCryptPasswordEncoder.encode(userDto.getPassword()),
                                   new HashSet<>(),
                                   new ArrayList<>(),
                                   new ArrayList<>(),
                                   new ArrayList<>());

        if (file != null) {
            try {
                user.getImages().add(imageService.uploadImage(file));
            } catch (Exception e) {
                throw new ImageUploadException("Failed to upload image for user", e);
            }
        }

        Optional<Role> role = roleRepository.findByRole(RoleEnum.USER);
        role.ifPresent(value -> user.getRoles().add(value));

        AppUser savedUser;
        try {
             savedUser = userRepository.save(user);
        } catch (Exception e) {
            throw new UserCreationException("Failed to create user");
        }
        return UserMapper.mapToUserDto(savedUser);
    }
}
