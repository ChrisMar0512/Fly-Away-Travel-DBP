package com.flyaway.travel.controller;

import com.flyaway.travel.dto.BookingRequestDTO;
import com.flyaway.travel.entity.Booking;
import com.flyaway.travel.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.flyaway.travel.dto.BookingResponseDTO;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    private BookingResponseDTO mapToDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getId());
        dto.setBookingDate(booking.getBookingDate());
        if (booking.getFlight() != null) {
            dto.setFlightId(booking.getFlight().getId());
            dto.setFlightNumber(booking.getFlight().getFlightNumber());
            dto.setEstDepartureTime(booking.getFlight().getDepartureTime());
            dto.setEstArrivalTime(booking.getFlight().getArrivalTime());
        }
        if (booking.getCustomer() != null) {
            dto.setCustomerId(booking.getCustomer().getId());
            dto.setCustomerFirstName(booking.getCustomer().getFirstName());
            dto.setCustomerLastName(booking.getCustomer().getLastName());
        }
        return dto;
    }

    @PostMapping("/flights/book")
    public ResponseEntity<BookingResponseDTO> bookFlight(@Valid @RequestBody BookingRequestDTO dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();

        Booking booking = bookingService.bookFlight(dto, userEmail);
        return new ResponseEntity<>(mapToDTO(booking), HttpStatus.OK);
    }

    @GetMapping("/flights/book/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingByIdAlias(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(mapToDTO(booking));
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(mapToDTO(booking));
    }
}
