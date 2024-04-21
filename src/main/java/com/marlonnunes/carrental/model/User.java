package com.marlonnunes.carrental.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_user_email", columnNames = {"email"}),
        @UniqueConstraint(name = "UK_user_cpf", columnNames = {"cpf"})
})
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

    @Column(unique = true)
    private String cpf;

    private Long storeId;

    private boolean disabled;

    private String verificationCode;

    private LocalDateTime verificationCodeValidUntil;

    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_createdBy"))
    private User createdBy;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_updatedBy"))
    private User updatedBy;

    public String getFullName(){
        return this.firstName + " " + this.getLastName();
    }
}
