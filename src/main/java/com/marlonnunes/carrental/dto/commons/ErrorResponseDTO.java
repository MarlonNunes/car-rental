package com.marlonnunes.carrental.dto.commons;

import java.util.List;

public record ErrorResponseDTO(
        List<String> errors
) {

    public static ErrorResponseDTO simpleError(String error){
        return new ErrorResponseDTO(List.of(error));
    }
}
