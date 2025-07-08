package com.bci.exercise.user_authentication.controller.exceptions;

import com.bci.exercise.user_authentication.exception.ApplicationException;
import com.bci.exercise.user_authentication.exception.Error;
import com.bci.exercise.user_authentication.exception.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleApplicationException(ApplicationException cae) {

        Error error = Error.builder()
                .code(cae.getErrorCode())
                .detail(cae.getErrorMessage())
                .timestamp(cae.getErrorTimestamp())
                .build();

        return new ResponseEntity<>(Collections.singletonList(error), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception e) {

        Error error = Error.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .detail(ErrorCodes.SYS_ERROR_CODE.message())
                .code(ErrorCodes.SYS_ERROR_CODE.code())
                .build();

        return new ResponseEntity<>(Collections.singletonList(error), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Error error = Error.builder()
                .code(ErrorCodes.VALUES_NOT_FORMATTED.code())
                .detail(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        return new ResponseEntity<>(Collections.singletonList(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintExceptions(ConstraintViolationException ex) {
        Optional<String> message = ex.getConstraintViolations().stream()
                .findFirst().map(ConstraintViolation::getMessage);

        Error error = Error.builder()
                .code(ErrorCodes.VALUES_NOT_FORMATTED.code())
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        message.ifPresent(error::setDetail);

        return new ResponseEntity<>(Collections.singletonList(error), HttpStatus.BAD_REQUEST);
    }
}
