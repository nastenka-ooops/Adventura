package com.neotour.mapper;

import com.neotour.dto.TourDto;
import com.neotour.entity.Image;
import com.neotour.entity.Tour;

import java.util.stream.Collectors;

public class TourMapper {
    public static TourDto mapToTourDto(Tour tour) {
        return new TourDto(
                tour.getId(),
                tour.getName(),
                tour.getDescription(),
                tour.getLocation().getLocation(),
                tour.getLocation().getCountry(),
                tour.getImages().stream().map(Image::getUrl).toList(),
                tour.getReviews().stream().map(ReviewMapper::mapToReviewDto).collect(Collectors.toList())
        );
    }
}
