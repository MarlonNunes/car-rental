package com.marlonnunes.carrental.dto.registration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreatePasswordDTO (
        @NotNull Long userId,
        @NotEmpty String verificationCode,
        @NotEmpty String password
){
}
