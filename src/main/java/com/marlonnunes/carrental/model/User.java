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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_user_role")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_role_user"))
    )
    private Set<Role> roles;

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
