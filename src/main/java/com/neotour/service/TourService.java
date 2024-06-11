package com.neotour.service;

import com.neotour.dto.TourDto;
import com.neotour.dto.TourListDto;
import com.neotour.entity.Tour;
import com.neotour.mapper.TourListMapper;
import com.neotour.mapper.TourMapper;
import com.neotour.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<TourListDto> getAllTours() {
        return tourRepository.findAll().stream()
                .map(TourListMapper::mapToTourListDto).toList();
    }
}
