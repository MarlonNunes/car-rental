package com.marlonnunes.carrental.dto.user;

import com.marlonnunes.carrental.dto.commons.IdNameDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;


import java.util.List;

public record CreateUserDTO(
        @Email
        String email,
        @NotEmpty
        String firstName,
        String lastName,
        @CPF
        @NotEmpty
        String cpf,
        List<IdNameDTO> roles
) {
}
