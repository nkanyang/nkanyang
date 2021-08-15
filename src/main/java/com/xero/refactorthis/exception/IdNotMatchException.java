package com.xero.refactorthis.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@Data
public class IdNotMatchException extends RuntimeException {
    public IdNotMatchException(UUID productId) {
        super("Product ID of option not match with given ID: " + productId + ".");
    }
}
