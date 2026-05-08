package com.flyaway.travel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class FlightCreateDTO {
    @NotBlank
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{3}$", message = "Debe tener 2 o 3 letras mayúsculas seguidas de 3 números")
    private String flightNumber;

    @NotBlank
    private String airlineName;

    @NotNull
    @JsonProperty("estDepartureTime")
    private LocalDateTime departureTime;

    @NotNull
    @JsonProperty("estArrivalTime")
    private LocalDateTime arrivalTime;

    @NotNull
    @Min(value = 1, message = "Debe ser mayor a 0 al momento de la creación")
    private Integer availableSeats;
}
