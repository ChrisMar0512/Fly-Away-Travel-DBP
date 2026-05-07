package com.flyaway.travel.controller;

import com.flyaway.travel.dto.FlightCreateDTO;
import com.flyaway.travel.entity.Flight;
import com.flyaway.travel.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping("/create")
    public ResponseEntity<Flight> createFlight(@Valid @RequestBody FlightCreateDTO dto) {
        Flight flight = flightService.createFlight(dto);
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Flight> flights = flightService.searchFlights(flightNumber, airlineName, startDate, endDate);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }
}
