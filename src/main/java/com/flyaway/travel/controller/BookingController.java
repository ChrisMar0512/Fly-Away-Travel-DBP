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

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/flights/book")
    public ResponseEntity<Booking> bookFlight(@Valid @RequestBody BookingRequestDTO dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();

        Booking booking = bookingService.bookFlight(dto, userEmail);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/flight/book/{id}")
    public ResponseEntity<Booking> getBookingByIdAlias(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }
}
