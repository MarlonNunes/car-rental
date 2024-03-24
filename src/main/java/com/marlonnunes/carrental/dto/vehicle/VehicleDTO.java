package com.marlonnunes.carrental.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.model.Vehicle;

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
    LocalDateTime updatedAt
    ){

    public static VehicleDTO fromVehicleWithUsers(Vehicle vehicle, User createdBy, User updatedBy){
        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getNumberPlate(),
                vehicle.getMake(),
                vehicle.getModel(),
                ColorDTO.fromColor(vehicle.getColor()),
                vehicle.getCreatedBy(),
                createdBy.getFullName(),
                vehicle.getUpdatedBy(),
                updatedBy.getFullName(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }

    public static VehicleDTO fromVehicle(Vehicle vehicle){
        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getNumberPlate(),
                vehicle.getMake(),
                vehicle.getModel(),
                ColorDTO.fromColor(vehicle.getColor()),
                vehicle.getCreatedBy(),
               null,
                vehicle.getUpdatedBy(),
                null,
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
}
