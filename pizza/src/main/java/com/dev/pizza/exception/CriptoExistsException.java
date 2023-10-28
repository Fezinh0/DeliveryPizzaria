package com.dev.pizza.exception;

import java.io.Serial;

public class CriptoExistsException extends Exception {

    public CriptoExistsException(String message) {
        super(message);
    }

    @Serial
    private static final long serialVersionUID = 1L;

}
