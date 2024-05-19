package com.marlonnunes.carrental.dto.user;

import com.marlonnunes.carrental.model.Role;

public record RoleDTO(
        Long id,
        String name,
        String description
) {

    public static RoleDTO fromRole(Role role){
        return new RoleDTO(
                role.getId(),
                role.getName(),
                role.getDescription()
        );
    }
}
