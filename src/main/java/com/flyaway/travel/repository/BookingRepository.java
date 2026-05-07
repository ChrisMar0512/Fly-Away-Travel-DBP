package com.flyaway.travel.repository;

import com.flyaway.travel.entity.Booking;
import com.flyaway.travel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    @Query("SELECT b FROM Booking b WHERE b.customer = :customer AND (" +
           "(b.flight.departureTime <= :newArrival AND b.flight.arrivalTime >= :newDeparture))")
    List<Booking> findOverlappingBookings(@Param("customer") User customer,
                                          @Param("newDeparture") LocalDateTime newDeparture,
                                          @Param("newArrival") LocalDateTime newArrival);
}
