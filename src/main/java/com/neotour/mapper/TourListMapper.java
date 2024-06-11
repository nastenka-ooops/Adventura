package com.neotour.mapper;

import com.neotour.dto.TourListDto;
import com.neotour.entity.Tour;

public class TourListMapper {
    public static TourListDto mapToTourListDto(Tour tour) {
        return new TourListDto(
                tour.getId(),
                tour.getName(),
                tour.getImages().get(0).getUrl()
        );
    }
}
