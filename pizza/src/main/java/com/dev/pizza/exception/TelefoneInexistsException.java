package com.dev.pizza.exception;

import java.io.Serial;

public class TelefoneInexistsException extends Exception{
    public TelefoneInexistsException(String message) {
        super(message);
    }

    @Serial
    private static final long serialVersionUID = 1L;
}