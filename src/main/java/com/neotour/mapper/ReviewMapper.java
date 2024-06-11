package com.neotour.mapper;

import com.neotour.dto.ReviewDto;
import com.neotour.entity.Review;
import com.neotour.entity.User;

public class ReviewMapper {
    public static ReviewDto mapToReviewDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getReview(),
                TourMapper.mapToTourDto(review.getTour()),
                UserMapper.mapUserToUserDto(review.getUser())
        );
    }
}
