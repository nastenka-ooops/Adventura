package com.neotour.mapper;

import com.neotour.dto.ReviewDto;
import com.neotour.entity.Review;

public class ReviewMapper {
    public static ReviewDto mapToReviewDto(Review review) {
        return new ReviewDto(
                review.getReview(),
                review.getUser().getUsername(),
                review.getUser().getImages().isEmpty() ? "" : review.getUser().getImages().get(0).getUrl(),
                review.getTour().getId()
        );
    }
}
