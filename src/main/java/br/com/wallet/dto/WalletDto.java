package br.com.wallet.dto;

import br.com.wallet.model.Wallet;
import br.com.wallet.model.enums.Status;

public record WalletDto(
        String id,
        String userId,
        double accountBalance,
        Status status
) {
    public WalletDto(Wallet wallet) {
        this(
                wallet.getId(),
                wallet.getUserId(),
                wallet.getAccountBalance(),
                wallet.getStatus()
        );
    }
}
