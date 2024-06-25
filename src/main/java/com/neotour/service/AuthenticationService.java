package com.neotour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neotour.dto.*;
import com.neotour.entity.AppUser;
import com.neotour.entity.Image;
import com.neotour.entity.Role;
import com.neotour.enums.RoleEnum;
import com.neotour.error.*;
import com.neotour.mapper.UserMapper;
import com.neotour.repository.RoleRepository;
import com.neotour.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(5);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService,
                                 ImageService imageService, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );

            String token = tokenService.generateToken(authentication);

            AppUser appUser = (AppUser) authentication.getPrincipal();
            Image profilePicture = null;
            if (!appUser.getImages().isEmpty()){
                profilePicture = appUser.getImages().get(0);
            }

            return new LoginResponse(
                    appUser.getUsername(),
                    profilePicture == null ? null : profilePicture.getUrl(),
                    token);

        } catch (AuthenticationException e) {
            if (e instanceof DisabledException) {
                throw new DisabledException("Account has not been enabled");
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        }
    }

    public RegistrationResponse registration(String request, MultipartFile file) {
        RegistrationRequest registrationRequest;

        try {
            registrationRequest = objectMapper.readValue(request, RegistrationRequest.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON format for UserDto");
        }

        if (usernameExists(registrationRequest.username())) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        if (emailExists(registrationRequest.email())) {
            throw new EmailAlreadyTakenException("Email is already taken");
        }
        if (phoneNumberExists(registrationRequest.phone())) {
            throw new PhoneAlreadyTakenException("Phone number is already taken");
        }
        if (!registrationRequest.password().equals(registrationRequest.confirmPassword())){
            throw new PasswordMismatchException("Passwords do not match");
        }

        AppUser user = new AppUser(registrationRequest.firstName(), registrationRequest.lastName(),
                registrationRequest.email(), registrationRequest.phone(), registrationRequest.username(),
                bCryptPasswordEncoder.encode(registrationRequest.password()),
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
            throw new UserCreationException("Failed to create user with this username");
        }
        return new RegistrationResponse(savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail(),
                savedUser.getPhone(), savedUser.getUsername(),
                savedUser.getImages().isEmpty() ? null : savedUser.getImages().get(0).getUrl());
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return userRepository.findByPhone(phoneNumber).isPresent();
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmailIgnoreCase(email).isPresent();
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isPresent();
    }

}
