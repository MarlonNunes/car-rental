package com.marlonnunes.carrental.dto.keycloak;

public record RoleDTO(
        String id,
        String name,
        String description,
        boolean composite
) {
}
