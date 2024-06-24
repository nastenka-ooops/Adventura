package com.neotour.controller;

import com.neotour.dto.BookingDto;
import com.neotour.dto.CreateBookingDto;
import com.neotour.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Create a new booking", description = "Create a new booking based on the provided booking data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
    })
    @PostMapping("/add")
    public ResponseEntity<BookingDto> createBooking(@RequestBody CreateBookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.createBooking(getCurrentUser(), bookingDto));
    }

    private static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
