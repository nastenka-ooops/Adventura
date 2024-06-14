package com.neotour.service;

import com.neotour.dto.CreateReviewDto;
import com.neotour.dto.ReviewDto;
import com.neotour.entity.AppUser;
import com.neotour.entity.Review;
import com.neotour.entity.Tour;
import com.neotour.error.ReviewCreationException;
import com.neotour.error.TourNotFoundException;
import com.neotour.error.UserNotFoundException;
import com.neotour.mapper.ReviewMapper;
import com.neotour.repository.ReviewRepository;
import com.neotour.repository.TourRepository;
import com.neotour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, TourRepository tourRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
    }

    public ReviewDto createReview(String username, CreateReviewDto reviewDto) {
        try {
            Optional<AppUser> user = userRepository.findByUsername(username);
            Optional<Tour> tour = tourRepository.findById(reviewDto.tourId());

            if (user.isEmpty()) {
                throw new UserNotFoundException("User with username " + username + " not found");
            }
            if (tour.isEmpty()) {
                throw new TourNotFoundException("Tour with ID " + reviewDto.tourId() + " not found");
            }


            Review review = new Review();
            review.setReview(reviewDto.review());
            review.setUser(user.get());
            review.setTour(tour.get());
            Review savedReview = reviewRepository.save(review);
            return ReviewMapper.mapToReviewDto(savedReview);
        }catch (Exception e){
            throw new ReviewCreationException("Failed to create review", e);
        }
    }

    public List<ReviewDto> getReviewsByTourId(Long tourId) {
        return reviewRepository.findByTourId(tourId)
                .stream().map(ReviewMapper::mapToReviewDto)
                .toList();
    }
}
