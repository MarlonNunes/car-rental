package com.marlonnunes.carrental.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
//@Getter
//@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_user_email", columnNames = {"email"}),
        @UniqueConstraint(name = "UK_user_cpf", columnNames = {"cpf"}),
        @UniqueConstraint(name = "UK_user_keycloakId", columnNames = {"keycloakId"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "FK_user_role"))
    private Role role;

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
