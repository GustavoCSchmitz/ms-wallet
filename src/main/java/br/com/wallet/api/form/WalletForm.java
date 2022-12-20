package br.com.wallet.api.form;

import br.com.wallet.model.Wallet;
import br.com.wallet.model.enums.Status;

public record WalletForm(
        String userId
) {
    public Wallet toWallet(WalletForm form) {
        return Wallet.builder()
                .userId(form.userId())
                .accountBalance(0.0)
                .status(Status.ACTIVE)
                .build();
    }
}
