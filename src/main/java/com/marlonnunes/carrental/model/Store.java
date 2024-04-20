package com.marlonnunes.carrental.model;

import com.marlonnunes.carrental.model.helpers.Contact;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String legalName;

    @Column(unique = true)
    private String cnpj;

    private String logoUrl;

    private List<Contact> contactList;

}
