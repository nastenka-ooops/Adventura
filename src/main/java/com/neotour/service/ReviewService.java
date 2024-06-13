package com.neotour.service;

import com.neotour.dto.CreateReviewDto;
import com.neotour.dto.ReviewDto;
import com.neotour.entity.AppUser;
import com.neotour.entity.Review;
import com.neotour.entity.Tour;
import com.neotour.mapper.ReviewMapper;
import com.neotour.mapper.TourMapper;
import com.neotour.repository.ReviewRepository;
import com.neotour.repository.TourRepository;
import com.neotour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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
        Review review = new Review();

        Optional<AppUser> user = userRepository.findByUsername(username);
        Optional<Tour> tour = tourRepository.findById(reviewDto.tourId());

        Review savedReview;

        if (user.isPresent() && tour.isPresent()) {
            review.setReview(reviewDto.review());
            review.setUser(user.get());
            review.setTour(tour.get());
            savedReview = reviewRepository.save(review);
            return ReviewMapper.mapToReviewDto(savedReview);
        }
        return null;
    }
}
