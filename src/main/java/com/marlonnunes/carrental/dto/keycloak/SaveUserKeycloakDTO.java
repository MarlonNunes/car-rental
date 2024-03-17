package com.marlonnunes.carrental.dto.keycloak;

import com.marlonnunes.carrental.dto.commons.IdNameDTO;
import com.marlonnunes.carrental.dto.user.CreateUserDTO;

import java.util.List;


public record SaveUserKeycloakDTO(
        String id,
        String username,
        Boolean enabled,
        Boolean emailVerified,
        String firstName,
        String lastName,
        String email,
        List<CredentialKeycloakDTO> credentials,
        List<String> requiredActions
) {

    public static SaveUserKeycloakDTO fromCreateUserDto(CreateUserDTO user){
        CredentialKeycloakDTO credential = CredentialKeycloakDTO.create(user.password());
        return new SaveUserKeycloakDTO(
                null,
                user.email(),
                true,
                true,
                user.firstName(),
                user.lastName(),
                user.email(),
                List.of(credential),
                null
        );
    }
}
