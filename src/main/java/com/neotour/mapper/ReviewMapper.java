package com.neotour.mapper;

import com.neotour.dto.ReviewDto;
import com.neotour.entity.Review;

public class ReviewMapper {
    public static ReviewDto mapToReviewDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getReview(),
                UserMapper.mapToUserDto(review.getUser())
        );
    }
}
