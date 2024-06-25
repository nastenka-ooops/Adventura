package com.neotour.service;

import com.neotour.dto.BookingDto;
import com.neotour.dto.CreateBookingDto;
import com.neotour.entity.AppUser;
import com.neotour.entity.Booking;
import com.neotour.entity.Tour;
import com.neotour.error.BookingCreationException;
import com.neotour.error.TourNotFoundException;
import com.neotour.error.TourUpdateException;
import com.neotour.error.UserNotFoundException;
import com.neotour.mapper.BookingMapper;
import com.neotour.repository.BookingRepository;
import com.neotour.repository.TourRepository;
import com.neotour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, TourRepository tourRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
    }

    public BookingDto createBooking(CreateBookingDto bookingDto) {
        String username = getCurrentUser();

        Booking booking = new Booking();

        Optional<AppUser> userOptional = userRepository.findByUsernameIgnoreCase(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with username " + username + " not found");
        }
        AppUser user = userOptional.get();

        Optional<Tour> tourOptional = tourRepository.findById(bookingDto.tourId());
        if (tourOptional.isEmpty()) {
            throw new TourNotFoundException("Tour with ID " + bookingDto.tourId() + " not found");
        }
        Tour tour = tourOptional.get();

        booking.setPhone(bookingDto.phone());
        booking.setComment(bookingDto.comment());
        booking.setPeopleAmount(bookingDto.people_amount());
        booking.setUser(user);
        booking.setTour(tour);

        Booking savedBooking;
        try {
            savedBooking = bookingRepository.save(booking);
        } catch (Exception e) {
            throw new BookingCreationException("Error occurred while saving the booking", e);
        }

        try {
            tour.setBookedAmount(tour.getBookedAmount() + 1);
            tourRepository.save(tour);
        } catch (Exception e) {
            throw new TourUpdateException("Error occurred while updating the tour", e);
        }

        return BookingMapper.mapToBookingDto(savedBooking);
    }

    private static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

