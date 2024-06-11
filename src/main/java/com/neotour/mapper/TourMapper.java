package com.neotour.mapper;

import com.neotour.dto.TourDto;
import com.neotour.entity.Image;
import com.neotour.entity.Tour;

public class TourMapper {
    public static TourDto mapToTourDto(Tour tour) {
        return new TourDto(
                tour.getId(),
                tour.getName(),
                tour.getDescription(),
                tour.getBookedAmount(),
                tour.getLocation().getLocation(),
                tour.getLocation().getCountry(),
                tour.getImages().stream().map(Image::getUrl).toList(),
                tour.getReviews().stream().map(ReviewMapper::mapToReviewDto).toList()
        );
    }
}
