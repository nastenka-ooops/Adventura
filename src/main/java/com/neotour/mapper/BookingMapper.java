package com.neotour.mapper;

import com.neotour.dto.BookingDto;
import com.neotour.entity.Booking;

public class BookingMapper {
    public static BookingDto mapToBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getPhone(),
                booking.getComment() != null ? booking.getComment() : "",
                booking.getUser().getUsername(),
                booking.getTour().getId()
        );
    }
}
