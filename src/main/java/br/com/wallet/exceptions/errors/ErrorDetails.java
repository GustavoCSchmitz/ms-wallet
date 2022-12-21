package br.com.wallet.exceptions.errors;

public record ErrorDetails(
        int statusCode,
        String timestamp,
        String message
) {
}
