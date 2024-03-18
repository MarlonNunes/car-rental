package com.marlonnunes.carrental.dto.user;

import com.marlonnunes.carrental.dto.commons.IdNameDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;


import java.util.List;

public record CreateUserDTO(
        @NotEmpty(message = "{dto.user.create-user.email.not-empty}")
        @Email(message = "{dto.user.create-user.email.invalid}")
        String email,
        @NotEmpty(message = "{dto.user.create-user.first-name.not-empty}")
        String firstName,
        String lastName,
        @CPF(message = "{dto.user.create-user.cpf.invalid}")
        @NotEmpty(message = "{dto.user.create-user.cpf.not-empty}")
        String cpf,
        List<IdNameDTO> roles
) {
}
