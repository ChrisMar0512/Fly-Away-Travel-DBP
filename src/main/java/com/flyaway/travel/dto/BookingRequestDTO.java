package com.flyaway.travel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDTO {
    @NotNull
    private Long flightId;
}
