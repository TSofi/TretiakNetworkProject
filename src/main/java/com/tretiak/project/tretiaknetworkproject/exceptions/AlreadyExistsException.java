package com.tretiak.project.tretiaknetworkproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistsException extends RuntimeException{
    private AlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException userByUsername(String username) {
        AlreadyExistsException exception = new AlreadyExistsException("Username: " + username + " already exists");
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }

    public static ResponseStatusException userByEmail(String email) {
        AlreadyExistsException exception = new AlreadyExistsException("Email: " + email + " already exists");
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }

    public static ResponseStatusException bookByIsbn(String isbn) {
        AlreadyExistsException exception = new AlreadyExistsException("Book isbn: " + isbn + " already exists");
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
