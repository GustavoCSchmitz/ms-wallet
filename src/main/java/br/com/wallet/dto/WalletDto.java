package br.com.wallet.dto;

import br.com.wallet.model.Wallet;
import br.com.wallet.model.enums.Status;

import java.util.List;

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

    public static List<WalletDto> convert(List<Wallet> wallets) {
        return wallets.stream()
                .map(WalletDto::new)
                .toList();
    }
}
