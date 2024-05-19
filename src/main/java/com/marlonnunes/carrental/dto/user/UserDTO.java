package com.marlonnunes.carrental.dto.user;

import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String keycloakId,
        String email,
        String cpf,
        RoleDTO role,
        boolean disabled,
        Long createdBy,
        String createdByName,
        LocalDateTime createdAt,
        Long updatedBy,
        String updateByName,
        LocalDateTime updatedAt


) {

    public static UserDTO fromUser(User user){
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getFullName(),
                user.getKeycloakId(),
                user.getEmail(),
                StringUtils.formatCpfOrCnpj(user.getCpf()),
                RoleDTO.fromRole(user.getRole()),
                user.isDisabled(),
                Optional.ofNullable(user.getCreatedBy()).map(User::getId).orElse(null),
                Optional.ofNullable(user.getCreatedBy()).map(User::getFullName).orElse(null),
                user.getCreatedAt(),
                Optional.ofNullable(user.getUpdatedBy()).map(User::getId).orElse(null),
                Optional.ofNullable(user.getUpdatedBy()).map(User::getFullName).orElse(null),
                user.getUpdatedAt()
        );
    }
}
