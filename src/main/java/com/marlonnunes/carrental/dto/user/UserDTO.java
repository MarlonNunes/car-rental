package com.marlonnunes.carrental.dto.user;

import com.marlonnunes.carrental.model.User;
import org.apache.commons.lang3.StringUtils;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String fullName,
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
                user.getFullName(),
                user.getKeycloakId(),
                user.getEmail(),
                user.getCpf(),
                user.getEnabled()
        );
    }
}
