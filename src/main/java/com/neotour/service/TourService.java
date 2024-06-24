package com.neotour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neotour.dto.CreateTourDto;
import com.neotour.dto.TourDto;
import com.neotour.dto.TourListDto;
import com.neotour.entity.Location;
import com.neotour.entity.Tour;
import com.neotour.enums.Continent;
import com.neotour.enums.Season;
import com.neotour.error.ImageUploadException;
import com.neotour.error.TourCreationException;
import com.neotour.error.TourNotFoundException;
import com.neotour.error.TourSaveException;
import com.neotour.mapper.TourListMapper;
import com.neotour.mapper.TourMapper;
import com.neotour.repository.LocationRepository;
import com.neotour.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final LocationRepository locationRepository;
    private final ImageService imageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public TourService(TourRepository tourRepository, LocationRepository locationRepository, ImageService imageService) {
        this.tourRepository = tourRepository;
        this.locationRepository = locationRepository;
        this.imageService = imageService;
    }

    public List<TourListDto> getAllTours() {
        return tourRepository.findAll().stream()
                .map(TourListMapper::mapToTourListDto).collect(Collectors.toList());
    }

    public TourDto createTour(String dto, MultipartFile file) {
        CreateTourDto tourDto;

        try {
            tourDto = objectMapper.readValue(dto, CreateTourDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON format for CreatingTourDto");
        }

        Location location = new Location(tourDto.location(), tourDto.country(), tourDto.continent());

        Optional<Location> repLocation = locationRepository.findByLocation(location.getLocation());
        if (repLocation.isPresent()) {
            location = repLocation.get();
        }
        else {
            location = locationRepository.save(location);
        }

        Tour tour = new Tour();
        tour.setName(tourDto.name());
        tour.setDescription(tourDto.description());
        tour.setLocation(location);
        tour.setFeatured(false);
        tour.setRecommendedSeason(tourDto.reccomendedSeason());
        tour.setImages(new ArrayList<>());
        tour.setReviews(new ArrayList<>());
        tour.setBookings(new ArrayList<>());

        try {
            tour.getImages().add(imageService.uploadImage(file));
        } catch (Exception e) {
            throw new ImageUploadException("Failed to upload image for user", e);
        }

        Tour savedTour;
        try {
            savedTour = tourRepository.save(tour);
        } catch (Exception e) {
            throw new TourCreationException("Failed to create tour");
        }
        return TourMapper.mapToTourDto(savedTour);
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

    public TourDto updateIsFeatured(Long id, boolean isFeatured) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new TourNotFoundException("Tour not found with id: " + id));
        tour.setFeatured(isFeatured);
        tourRepository.save(tour);
        return TourMapper.mapToTourDto(tour);
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
