package br.com.wallet.exceptions;

import java.io.Serial;

public class WalletException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1671097244785659949L;

    public WalletException(String message) {
        super(message);
    }
}
