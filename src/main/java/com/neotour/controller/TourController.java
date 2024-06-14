package com.neotour.controller;

import com.neotour.dto.TourDto;
import com.neotour.dto.TourListDto;
import com.neotour.entity.Continent;
import com.neotour.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tours")
@Tag(name = "Tour Controller", description = "Operations related to tours")
public class TourController {

    private final TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @Operation(summary = "Get all tours", description = "Retrieve a list of all tours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<TourListDto>> getAllTours() {
        List<TourListDto> tours = tourService.getAllTours();
        return ResponseEntity.ok(tours);
    }

    @Operation(summary = "Get tour by ID", description = "Get tour details based on the provided tour ID.")
    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getTourById(
            @Parameter(description = "ID of the tour to be obtained. Cannot be empty.", example = "1") @PathVariable Long id) {
        Optional<TourDto> customerDto = tourService.getTourById(id);
        return customerDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get tours by continent", description = "Retrieve a list of tours based on the specified continent.")
    @GetMapping("/by-continent/{continent}")
    public ResponseEntity<List<TourListDto>> getToursByContinent(
            @Parameter(description = "Continent name to filter tours by.", example = "EUROPE", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Continent.class)) @PathVariable Continent continent) {
        List<TourListDto> tours = tourService.findToursByContinent(continent);
        return ResponseEntity.ok(tours);
    }

    @Operation(summary = "Get Recommended Tours by Current Season",
            description = "Retrieves a list of recommended tours based on the current season.")
    @ApiResponse(responseCode = "200", description = "Successful operation, returns a list of tours.")
    @GetMapping("/recommended")
    public ResponseEntity<List<TourListDto>> getRecommendedToursByCurrentSeason() {
        List<TourListDto> tours =  tourService.findRecommendedToursByCurrentSeason();
        return ResponseEntity.ok(tours);
    }

    @Operation(summary = "Get most visited tours", description = "Retrieve a list of the most visited tours.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of most visited tours returned successfully"),
            @ApiResponse(responseCode = "404", description = "No tours found", content = @Content),
    })
    @GetMapping("/mostVisited")
    public ResponseEntity<List<TourListDto>> getMostVisitedTours() {
        List<TourListDto> tours = tourService.findMostVisitedTours();
        return ResponseEntity.ok(tours);
    }

    @Operation(summary = "Get popular tours", description = "Fetches a list of the most popular tours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TourListDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/popular")
    public ResponseEntity<List<TourListDto>> getPopularTours() {
        List<TourListDto> tours = tourService.findPopularTours();
        return ResponseEntity.ok(tours);
    }

    @Operation(summary = "Get featured tours", description = "Fetches a list of the featured tours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TourListDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/featured")
    public ResponseEntity<List<TourListDto>> getFeaturedTours() {
        List<TourListDto> tours = tourService.findFeaturedTours();
        return ResponseEntity.ok(tours);
    }
}
