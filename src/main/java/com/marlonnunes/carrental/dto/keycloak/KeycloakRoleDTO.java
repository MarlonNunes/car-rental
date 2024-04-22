package com.marlonnunes.carrental.dto.keycloak;

public record KeycloakRoleDTO(
        String id,
        String name,
        String description,
        boolean composite
) {
}
