package com.flyaway.travel.service;

import com.flyaway.travel.dto.FlightCreateDTO;
import com.flyaway.travel.entity.Flight;
import com.flyaway.travel.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public Flight createFlight(FlightCreateDTO dto) {
        if (flightRepository.existsByFlightNumber(dto.getFlightNumber())) {
            throw new IllegalArgumentException("Flight number already exists");
        }

        if (dto.getDepartureTime().isAfter(dto.getArrivalTime()) || dto.getDepartureTime().isEqual(dto.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time must be strictly before arrival time");
        }

        Flight flight = new Flight();
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setAirlineName(dto.getAirlineName());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setAvailableSeats(dto.getAvailableSeats());

        return flightRepository.save(flight);
    }

    public List<Flight> searchFlights(String flightNumber, String airlineName, LocalDateTime startDate, LocalDateTime endDate) {
        return flightRepository.searchFlights(flightNumber, airlineName, startDate, endDate);
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));
    }
}
