package br.com.wallet.exceptions.errors;

public record Violation(
        String fieldName,
        String message
) {
}
