package com.marlonnunes.carrental.model;

import com.marlonnunes.carrental.model.enums.Color;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_vehicle_numberPlate", columnNames = {"numberPlate"})
})
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

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_vehicle_createdBy"))
    private User createdBy;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_vehicle_updatedBy"))
    private User updatedBy;

    private LocalDateTime updatedAt;

    private BigDecimal maxDailyValue;

    private BigDecimal minDailyValue;
}
