package com.flyaway.travel.service;

import com.flyaway.travel.dto.BookingRequestDTO;
import com.flyaway.travel.entity.Booking;
import com.flyaway.travel.entity.Flight;
import com.flyaway.travel.entity.User;
import com.flyaway.travel.repository.BookingRepository;
import com.flyaway.travel.repository.FlightRepository;
import com.flyaway.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Booking bookFlight(BookingRequestDTO dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Flight flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));

        // Validation 1: Past / Transit
        if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book a flight that is in the past or in transit");
        }

        // Validation 2: Available seats
        if (flight.getAvailableSeats() <= 0) {
            throw new IllegalArgumentException("No available seats for this flight");
        }

        // Validation 3: Overlapping schedules
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(user, flight.getDepartureTime(), flight.getArrivalTime());
        if (!overlapping.isEmpty()) {
            throw new IllegalArgumentException("Schedule conflict: you already have a booking overlapping with this flight");
        }

        // Reduce seats
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        // Create booking
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setCustomer(user);
        booking.setCustomerName(user.getFirstName() + " " + user.getLastName());
        booking.setBookingDate(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);

        generateBookingFile(savedBooking);

        return savedBooking;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    private void generateBookingFile(Booking booking) {
        String dir = new java.io.File("/host").exists() ? "/host/" : "";
        String filename = dir + "flight_booking_email_" + booking.getId() + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("bookingDate: " + booking.getBookingDate().toString() + "\n");
            writer.write("Customer: " + booking.getCustomerName() + "\n");
            writer.write("Flight Number: " + booking.getFlight().getFlightNumber() + "\n");
            writer.write("Departure: " + booking.getFlight().getDepartureTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n");
            writer.write("Arrival: " + booking.getFlight().getArrivalTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
