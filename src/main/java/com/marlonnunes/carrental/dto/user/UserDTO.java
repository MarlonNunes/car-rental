package com.marlonnunes.carrental.dto.user;

import com.marlonnunes.carrental.model.User;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String keycloakId,
        String email,
        String cpf,
        Boolean enabled


) {

    public static UserDTO fromUser(User user){
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getKeycloakId(),
                user.getEmail(),
                user.getCpf(),
                user.getEnabled()
        );
    }
}
