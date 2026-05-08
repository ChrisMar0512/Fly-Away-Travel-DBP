package com.flyaway.travel.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class FlightCreateManyDTO {
    @NotEmpty
    @Valid
    private List<FlightCreateDTO> inputs;
}
