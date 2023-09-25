package com.breadcrumb.kit.accounting.crypto.handler;

import com.breadcrumb.kit.accounting.crypto.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler
 * @author Maksym Khudiakov
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleApplicationException(ApplicationException e) {
        LOG.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getCode(), e.getMessage()));
    }
}
