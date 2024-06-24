package com.neotour.service;

import com.neotour.dto.LoginRequest;
import com.neotour.dto.LoginResponse;
import com.neotour.dto.RegistrationRequest;
import com.neotour.dto.RegistrationResponse;
import com.neotour.entity.AppUser;
import com.neotour.entity.Image;
import com.neotour.enums.RoleEnum;
import com.neotour.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
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
}
