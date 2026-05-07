package com.flyaway.travel.repository;

import com.flyaway.travel.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE " +
           "(:flightNumber IS NULL OR f.flightNumber LIKE %:flightNumber%) AND " +
           "(:airlineName IS NULL OR f.airlineName LIKE %:airlineName%) AND " +
           "(:startDate IS NULL OR f.departureTime >= :startDate) AND " +
           "(:endDate IS NULL OR f.departureTime <= :endDate)")
    List<Flight> searchFlights(@Param("flightNumber") String flightNumber,
                               @Param("airlineName") String airlineName,
                               @Param("startDate") LocalDateTime startDate,
                               @Param("endDate") LocalDateTime endDate);

    boolean existsByFlightNumber(String flightNumber);
}
