package com.neotour.service;

import com.neotour.dto.TourDto;
import com.neotour.dto.TourListDto;
import com.neotour.entity.Tour;
import com.neotour.enums.Continent;
import com.neotour.enums.Season;
import com.neotour.error.TourNotFoundException;
import com.neotour.error.TourSaveException;
import com.neotour.mapper.TourListMapper;
import com.neotour.mapper.TourMapper;
import com.neotour.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                .map(TourListMapper::mapToTourListDto).collect(Collectors.toList());
    }

    public Optional<TourDto> getTourById(Long id) {
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isEmpty()) {
            throw new TourNotFoundException("Tour not found with ID: " + id);
        }
        Tour tourEntity = tour.get();
        tourEntity.setVisitedAmount(tourEntity.getVisitedAmount() + 1);
        try {
            tourRepository.save(tourEntity);
        } catch (Exception ex) {
            throw new TourSaveException("Failed to update tour with ID: " + id, ex);
        }
        return tour.map(TourMapper::mapToTourDto);
    }

    public List<TourListDto> findToursByContinent(Continent continent) {
        return tourRepository.findByLocationContinent(continent).stream()
                .map(TourListMapper::mapToTourListDto).collect(Collectors.toList());
    }

    public List<TourListDto> findRecommendedToursByCurrentSeason() {
        Season currentSeason = SeasonDetector.getCurrentSeason();
        return tourRepository.findByRecommendedSeason(currentSeason).stream()
                .map(TourListMapper::mapToTourListDto).collect(Collectors.toList());
    }

    public List<TourListDto> findMostVisitedTours() {
        return tourRepository.findAllOrderByBookedAmountDesc().stream()
                .map(TourListMapper::mapToTourListDto).collect(Collectors.toList());
    }

    public List<TourListDto> findPopularTours() {
        return tourRepository.findAllOrderByVisitedAmountDesc().stream()
                .map(TourListMapper::mapToTourListDto).collect(Collectors.toList());
    }

    public List<TourListDto> findFeaturedTours() {
        return tourRepository.findAllByFeaturedIsTrue().stream()
                .map(TourListMapper::mapToTourListDto).collect(Collectors.toList());
    }
}
