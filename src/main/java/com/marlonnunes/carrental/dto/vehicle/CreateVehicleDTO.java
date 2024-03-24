package com.marlonnunes.carrental.dto.vehicle;

import com.marlonnunes.carrental.model.enums.Color;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateVehicleDTO(
        @NotEmpty(message = "{dto.vehicle.create-vehicle.number-plate}") String numberPlate,
        @NotEmpty(message = "{dto.vehicle.create-vehicle.make}") String make,
        @NotEmpty(message = "{dto.vehicle.create-vehicle.model}") String model,
        @NotNull(message = "{dto.vehicle.create-vehicle.color}") Color color

) {
}
