package com.xero.refactorthis.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@Data
public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException() {
        super("ID not found.");
    }
    public IdNotFoundException(UUID id) {
        super("ID: " + id + " not found.");
    }
    public IdNotFoundException(UUID id, String name) {
        super("ID: " + id + " of " + name + " not found.");
    }
}
