package com.marlonnunes.carrental.controller;

import com.marlonnunes.carrental.dto.commons.ErrorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class GlobalHandleExceptionController {

    @Autowired
    private MessageSource messageSource;


    private String getMessage(String messageCode){
        return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
    }
    @ExceptionHandler(value = ResponseStatusException.class)
    private ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException e){
                ErrorResponseDTO error = ErrorResponseDTO.simpleError(this.getMessage(e.getReason()));
                return new ResponseEntity<>(error, e.getStatusCode());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        List<String> errors = bindingResult.getAllErrors().stream()
                .map(error -> error.getDefaultMessage()).toList();

        return new ResponseEntity<>(new ErrorResponseDTO(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NullPointerException.class)
    private ResponseEntity<ErrorResponseDTO> handleNullPointerException(NullPointerException e){
        return new ResponseEntity<>(ErrorResponseDTO.simpleError(this.getMessage("controller.global-handle-exception.null-pointer")), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
