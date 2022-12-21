package br.com.wallet.exceptions;

import java.io.Serial;

public class NotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 2331458591179650131L;

    public NotFoundException(String message) {
        super(message);
    }
}
