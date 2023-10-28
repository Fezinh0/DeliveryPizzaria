package com.dev.pizza.exception;

import java.io.Serial;

public class TelefoneExistsException extends Exception{
    public TelefoneExistsException(String message) {
        super(message);
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
