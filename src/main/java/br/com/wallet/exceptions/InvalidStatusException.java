package br.com.wallet.exceptions;

import java.io.Serial;

public class InvalidStatusException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1090131047936259172L;

    public InvalidStatusException(String message) {
        super(message);
    }
}
