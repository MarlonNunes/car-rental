package com.marlonnunes.carrental.model;

import com.marlonnunes.carrental.model.enums.Color;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numberPlate;

    private String model;

    private String make;

    private Integer modelYear;

    private Integer manufactureYear;

    private Color color;

    private Long createdBy;

    private LocalDateTime createdAt;

    private Long updatedBy;

    private LocalDateTime updatedAt;

    private BigDecimal maxDailyValue;

    private BigDecimal minDailyValue;
}
