package com.neotour.controller;

import com.neotour.dto.CreateTourDto;
import com.neotour.dto.TourDto;
import com.neotour.dto.TourListDto;
import com.neotour.enums.Continent;
import com.neotour.enums.TourCategory;
import com.neotour.error.InvalidCategoryException;
import com.neotour.service.TourService;
import io.swagger.v3.oas.annotations.Hidden;
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
import org.springframework.web.multipart.MultipartFile;

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


    @Operation(summary = "Get tours by category", description = "Retrieve a list of tours based on the specified category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TourListDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/by-category/{category}")
    public ResponseEntity<List<TourListDto>> getToursByCategory(
            @Parameter(description = "Category to filter tours by.", example = "RECOMMENDED", schema = @Schema(implementation = TourCategory.class))
            @PathVariable TourCategory category,
            @RequestParam(value = "continent", required = false) Continent continent) {

        List<TourListDto> tours;

        switch (category) {
            case BY_CONTINENT:
                if (continent == null) {
                    throw new InvalidCategoryException("Continent parameter is required for BY_CONTINENT category.");
                }
                tours = tourService.findToursByContinent(continent);
                break;
            case RECOMMENDED:
                tours = tourService.findRecommendedToursByCurrentSeason();
                break;
            case MOST_VISITED:
                tours = tourService.findMostVisitedTours();
                break;
            case POPULAR:
                tours = tourService.findPopularTours();
                break;
            case FEATURED:
                tours = tourService.findFeaturedTours();
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(tours);
    }

    @Operation(summary = "Update tour's featured status", description = "Updates the is_featured field for a specified tour.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tour updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TourDto.class))),
            @ApiResponse(responseCode = "404", description = "Tour not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/{id}/feature")
    public ResponseEntity<TourDto> updateFeaturedStatus(
            @Parameter(description = "ID of the tour to be updated", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Desired value of is_featured (true/false)", example = "true")
            @RequestParam boolean isFeatured) {
        TourDto updatedTour = tourService.updateIsFeatured(id, isFeatured);
        return ResponseEntity.ok(updatedTour);
    }

    @Operation(
            summary = "Create a new tour",
            description = "Creates a new tour with the provided details and file."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tour created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TourDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<TourDto> createTour(
            @RequestPart(name = "tour") @Parameter(description = "JSON string representing the tour details",
                    schema = @Schema(implementation = CreateTourDto.class))
            String createTourDto,
            @RequestPart(name = "file") @Parameter(description = "File to be uploaded along with the tour details")
            MultipartFile file) {
        return ResponseEntity.ok(tourService.createTour(createTourDto, file));
    }

    @Operation(summary = "Get tour by ID", description = "Get tour details based on the provided tour ID.")
    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getTourById(
            @Parameter(description = "ID of the tour to be obtained. Cannot be empty.", example = "1") @PathVariable Long id) {
        Optional<TourDto> customerDto = tourService.getTourById(id);
        return customerDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Hidden
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

    @Hidden
    @Operation(summary = "Get tours by continent", description = "Retrieve a list of tours based on the specified continent.")
    @GetMapping("/by-continent/{continent}")
    public ResponseEntity<List<TourListDto>> getToursByContinent(
            @Parameter(description = "Continent name to filter tours by.", example = "EUROPE", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Continent.class)) @PathVariable Continent continent) {
        List<TourListDto> tours = tourService.findToursByContinent(continent);
        return ResponseEntity.ok(tours);
    }

    @Hidden
    @Operation(summary = "Get Recommended Tours by Current Season",
            description = "Retrieves a list of recommended tours based on the current season.")
    @ApiResponse(responseCode = "200", description = "Successful operation, returns a list of tours.")
    @GetMapping("/recommended")
    public ResponseEntity<List<TourListDto>> getRecommendedToursByCurrentSeason() {
        List<TourListDto> tours = tourService.findRecommendedToursByCurrentSeason();
        return ResponseEntity.ok(tours);
    }

    @Hidden
    @Operation(summary = "Get most visited tours", description = "Retrieve a list of the most visited tours.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of most visited tours returned successfully"),
            @ApiResponse(responseCode = "404", description = "No tours found", content = @Content),
    })
    @GetMapping("/most-visited")
    public ResponseEntity<List<TourListDto>> getMostVisitedTours() {
        List<TourListDto> tours = tourService.findMostVisitedTours();
        return ResponseEntity.ok(tours);
    }

    @Hidden
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

    @Hidden
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
