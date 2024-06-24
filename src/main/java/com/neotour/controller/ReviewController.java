package com.neotour.controller;

import com.neotour.dto.CreateReviewDto;
import com.neotour.dto.ReviewDto;
import com.neotour.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/add")
    public ResponseEntity<ReviewDto> createReview(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data for the new review",
                    required = true, content = @Content(schema = @Schema(implementation = CreateReviewDto.class)))
            @RequestBody CreateReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.createReview(getCurrentUser(), reviewDto));
    }


    @Operation(summary = "Get reviews by tour ID", description = "Get all reviews associated with a specific tour ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reviews returned successfully"),
            @ApiResponse(responseCode = "404", description = "Tour ID not found", content = @Content)
    })
    @GetMapping("/by-tour/{tourId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByTourId(
            @Parameter(description = "ID of the tour to fetch reviews for", example = "123") @PathVariable Long tourId) {
        List<ReviewDto> reviews = reviewService.getReviewsByTourId(tourId);
        return ResponseEntity.ok(reviews);
    }

    private static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
