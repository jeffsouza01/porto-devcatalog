package com.porto.devcatalog.Porto.controllers.exceptions;

import com.porto.devcatalog.Porto.services.exceptions.DatabaseException;
import com.porto.devcatalog.Porto.services.exceptions.ResourceNotFoundExceptions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundExceptions.class)
    public StandardError entityNotFound(ResourceNotFoundExceptions e, HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setError("Item Not Found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return err;
    }

    @ExceptionHandler(DatabaseException.class)
    public StandardError databaseError(ResourceNotFoundExceptions e, HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setError("Database Exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return err;
    }
}
