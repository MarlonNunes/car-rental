package com.marlonnunes.carrental.dto.user;

import com.marlonnunes.carrental.dto.commons.IdNameDTO;

import java.util.List;

public record CreateUserDTO(
        String email,
        String firstName,
        String lastName,
        String password,
        List<IdNameDTO> roles
) {
}
