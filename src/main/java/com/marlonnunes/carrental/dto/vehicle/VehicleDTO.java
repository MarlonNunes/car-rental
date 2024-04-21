package com.marlonnunes.carrental.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.model.Vehicle;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VehicleDTO (
    Long id,
    String numberPlate,
    String make,
    String model,
    ColorDTO color,
    Long createdBy,
    String createdByName,
    Long updatedBy,
    String updatedByName,

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime createdAt,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime updatedAt,
    BigDecimal maxDailyValue,
    BigDecimal minDailyValue,
    Integer modelYear,
    Integer manufactureYear
    ){


    public static VehicleDTO fromVehicle(Vehicle vehicle){
        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getNumberPlate(),
                vehicle.getMake(),
                vehicle.getModel(),
                ColorDTO.fromColor(vehicle.getColor()),
                vehicle.getCreatedBy().getId(),
               vehicle.getCreatedBy().getFullName(),
                vehicle.getUpdatedBy().getId(),
                vehicle.getUpdatedBy().getFullName(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt(),
                vehicle.getMaxDailyValue(),
                vehicle.getMinDailyValue(),
                vehicle.getModelYear(),
                vehicle.getManufactureYear()
        );
    }
}
