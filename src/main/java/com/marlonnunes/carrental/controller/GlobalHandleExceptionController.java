package com.marlonnunes.carrental.controller;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.marlonnunes.carrental.dto.commons.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalHandleExceptionController {

    @Autowired
    private MessageSource messageSource;


    private String getMessage(String messageCode){
        return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException e){
                ErrorResponseDTO error = ErrorResponseDTO.simpleError(this.getMessage(e.getReason()));
                return new ResponseEntity<>(error, e.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        List<String> errors = bindingResult.getAllErrors().stream()
                .map(error -> error.getDefaultMessage()).toList();

        return new ResponseEntity<>(new ErrorResponseDTO(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDTO> handleNullPointerException(NullPointerException e){
        log.error("an error has occurred", e);
        return new ResponseEntity<>(ErrorResponseDTO.simpleError(this.getMessage("controller.global-handle-exception.null-pointer")), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
        log.error("an error has ocurred", e);
        return new ResponseEntity<>(ErrorResponseDTO.simpleError(this.getMessage("controller.global-handle-exception.missing-request-paraneter")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameterException(HttpRequestMethodNotSupportedException e){
        log.error("an error has ocurred", e);
        return new ResponseEntity<>(ErrorResponseDTO.simpleError(this.getMessage("controller.global-handle-exception.method-not-supported")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameterException(NoResourceFoundException e){
        log.error("an error has ocurred", e);
        return new ResponseEntity<>(ErrorResponseDTO.simpleError(this.getMessage("controller.global-handle-exception.no-resource-found")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<ErrorResponseDTO> handleMismatchedInputException(MismatchedInputException e){
        log.error("an error has ocurred", e);
        return new ResponseEntity<>(ErrorResponseDTO.simpleError(this.getMessage("controller.global-handle-exception.mismatched-input-exception")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleMismatchedInputException(HttpMessageNotReadableException e){
        log.error("an error has ocurred", e);
        return new ResponseEntity<>(ErrorResponseDTO.simpleError(this.getMessage("controller.global-handle-exception.mismatched-input-exception")), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e){
        log.error("an error has occurred", e);
        return new ResponseEntity<>(ErrorResponseDTO.simpleError(this.getMessage("controller.global-handle-exception.exception")), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
