package com.itinov.films.exceptions;

import com.itinov.films.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error("Entity not found : {}", ex.getMessage());

        return new ResponseEntity<>("Entity not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}