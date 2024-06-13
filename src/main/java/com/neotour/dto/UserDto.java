package com.neotour.dto;

import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private List<ImageDto> images;

    public UserDto() {
    }

    public UserDto(Long id, String username, String password, List<ImageDto> images) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }
}
