package com.flyaway.travel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightCreateDTO {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]{1,6}$", message = "Alfanumérico, máximo 6 caracteres")
    private String flightNumber;

    @NotBlank
    private String airlineName;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    @Min(value = 1, message = "Debe ser mayor a 0 al momento de la creación")
    private Integer availableSeats;
}
