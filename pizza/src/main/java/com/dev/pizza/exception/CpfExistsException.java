package com.dev.pizza.exception;

import java.io.Serial;

public class CpfExistsException extends Exception {

    public CpfExistsException(String message) {
        super(message);
    }

    @Serial
    private static final long serialVersionUID = 1L;

}