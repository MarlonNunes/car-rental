package com.marlonnunes.carrental.dto.store;

import com.marlonnunes.carrental.dto.contact.ContactDTO;
import com.marlonnunes.carrental.model.Store;
import com.marlonnunes.carrental.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public record StoreDTO(
        Long id,
        String legalName,
        String cnpj,
        String logoUrl,
        List<ContactDTO> contacts,
        String city,
        String state,
        String addressStreet,
        Integer addressNumber,

        String district,
        String zipCode,
        LocalDateTime createdAt,
        Long createdBy,
        String createdByName,
        LocalDateTime updatedAt,
        Long updatedBy,
        String updatedByName

) {

    public static StoreDTO fromStore(Store store){
        return new StoreDTO(
                store.getId(),
                store.getLegalName(),
                StringUtils.formatCpfOrCnpj(store.getCnpj()),
                store.getLogoUrl(),
                ContactDTO.fromContactList(store.getContacts()),
                store.getCity(),
                store.getState(),
                store.getAddressStreet(),
                store.getAddressNumber(),
                store.getDistrict(),
                StringUtils.formatCep(store.getZipCode()),
                store.getCreatedAt(),
                store.getCreatedBy().getId(),
                store.getCreatedBy().getFullName(),
                store.getUpdatedAt(),
                store.getUpdatedBy().getId(),
                store.getUpdatedBy().getFullName()
        );
    }
}
