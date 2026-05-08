package com.flyaway.travel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private Long id;
    private LocalDateTime bookingDate;
    private Long flightId;
    private String flightNumber;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;

    @JsonProperty("estDepartureTime")
    private LocalDateTime estDepartureTime;

    @JsonProperty("estArrivalTime")
    private LocalDateTime estArrivalTime;
}
