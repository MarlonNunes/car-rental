package com.marlonnunes.carrental.dto.registration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreatePasswordDTO (
        @NotNull(message = "{dto.registration.create-password.user-id}") Long userId,
        @NotEmpty(message = "{dto.registration.create-password.verification-code}") String verificationCode,
        @NotEmpty(message = "{dto.registration.create-password.password}") String password
){
}
