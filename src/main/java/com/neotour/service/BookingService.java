package com.neotour.service;

import com.neotour.dto.BookingDto;
import com.neotour.dto.CreateBookingDto;
import com.neotour.entity.AppUser;
import com.neotour.entity.Booking;
import com.neotour.entity.Review;
import com.neotour.entity.Tour;
import com.neotour.mapper.BookingMapper;
import com.neotour.repository.BookingRepository;
import com.neotour.repository.TourRepository;
import com.neotour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
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

    public BookingDto createBooking(String username, CreateBookingDto bookingDto) {
        Booking booking = new Booking();

        Optional<AppUser> user = userRepository.findByUsername(username);
        Optional<Tour> tour = tourRepository.findById(bookingDto.tourId());

        Booking savedBooking;

        if (user.isPresent() && tour.isPresent()) {
            booking.setPhone(bookingDto.phone());
            booking.setComment(bookingDto.comment());
            booking.setUser(user.get());
            booking.setTour(tour.get());

            savedBooking = bookingRepository.save(booking);

            tour.get().setBookedAmount(tour.get().getBookedAmount()+1);
            tourRepository.save(tour.get());

            return BookingMapper.mapToBookingDto(savedBooking);
        }
        return null;
    }
}

