package br.com.wallet.dto;

import java.math.BigDecimal;

public record WalletResponseDto(
        String id,
        BigDecimal accountBalance
) {
}
