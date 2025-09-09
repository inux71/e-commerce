package com.grabieckacper.ecommerce.shared.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    // 404 - Not found
    @ExceptionHandler(value = { EntityNotFoundException.class, FileNotFoundException.class, UsernameNotFoundException.class })
    public ResponseEntity<Object> handle404NotFound(RuntimeException ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        ObjectError error = ex.getBindingResult()
                .getAllErrors()
                .getFirst();
        String field = ((FieldError) error).getField();
        String errorMessage = field + ": " + error.getDefaultMessage();

        return handleExceptionInternal(ex, errorMessage, headers, status, request);
    }
}
