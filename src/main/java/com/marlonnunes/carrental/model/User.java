package com.marlonnunes.carrental.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String keycloakId;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String cpf;

    private Boolean enabled;
}
