package br.com.wallet.exceptions.errors;

import java.util.List;

public record ViolationDetails(
        int statusCode,
        String timestamp,
        String message,
        List<Violation> violations
) {
}
