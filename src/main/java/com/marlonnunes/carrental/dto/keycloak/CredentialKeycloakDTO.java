package com.marlonnunes.carrental.dto.keycloak;

public record CredentialKeycloakDTO(
        String id,
        String type,
        String userLabel,
        String value

) {


    public static CredentialKeycloakDTO create(String password){
        return new CredentialKeycloakDTO(
                null,
                "password",
                "passowrd",
                password
        );
    }
}
