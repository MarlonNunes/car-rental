package com.marlonnunes.carrental.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_store_cnpj", columnNames = {"cnpj"})
})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String legalName;
    private String cnpj;
    private String logoUrl;
    private String zipCode;
    private String state;
    private String city;
    private String district;
    private String addressStreet;
    private Integer addressNumber;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "FK_contact_store"))
    Set<Contact> contacts;

    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_store_createdBy"))
    private User createdBy;

    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_store_updatedBy"))
    private User updatedBy;

}
