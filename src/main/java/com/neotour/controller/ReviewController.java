package com.neotour.controller;

import com.neotour.dto.CreateReviewDto;
import com.neotour.dto.ReviewDto;
import com.neotour.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Create a new review", description = "Creates a new review based on the provided review data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, the review could not be created",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/reviews")
    public ResponseEntity<ReviewDto> createReview(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data for the new review",
                    required = true, content = @Content(schema = @Schema(implementation = CreateReviewDto.class)))
            @RequestBody CreateReviewDto reviewDto) {
        ReviewDto savedReview = reviewService.createReview(getCurrentUser(), reviewDto);
        if (savedReview != null) {
            return ResponseEntity.ok(savedReview);
        }
        return ResponseEntity.badRequest().build();
    }

    static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
