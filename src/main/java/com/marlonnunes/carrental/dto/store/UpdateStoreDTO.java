package com.marlonnunes.carrental.dto.store;

import com.marlonnunes.carrental.model.Contact;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;
import java.util.Set;

public record UpdateStoreDTO(
        @NotNull(message = "{dto.store.update.id}") Long id,
        @NotEmpty(message = "{dto.store.create.legal-name}") String legalName,
        @NotEmpty(message = "{dto.store.create.cnpj.empty}")
        @CNPJ(message = "{dto.store.create.cnpj.invalid}")
        String cnpj,
        String logoUrl,
        @NotEmpty(message = "{dto.store.create.contact-list}")
        Set<Contact> contacts,
        String city,
        String state,
        String addressStreet,
        Integer addressNumber,
        String district,
        String zipCode
) {
}
