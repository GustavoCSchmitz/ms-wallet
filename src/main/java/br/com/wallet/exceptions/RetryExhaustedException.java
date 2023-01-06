package br.com.wallet.exceptions;

public class RetryExhaustedException extends RuntimeException {

    public RetryExhaustedException(final String message, final Throwable throwable) {

        super(message, throwable);
    }

}
