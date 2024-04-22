package com.marlonnunes.carrental.model;

import com.marlonnunes.carrental.model.enums.ContactType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContactType type;

    private String value;

    private boolean disabled;
}
