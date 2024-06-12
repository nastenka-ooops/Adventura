package com.neotour.mapper;

import com.neotour.dto.ImageDto;
import com.neotour.entity.Image;

public class ImageMapper {
    public static ImageDto mapToImageDto(Image image) {
        return new ImageDto(
                image.getId(),
                image.getName(),
                image.getUrl()
        );
    }
}
