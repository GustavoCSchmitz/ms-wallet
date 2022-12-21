package br.com.wallet.api.form;

import br.com.wallet.model.Wallet;
import br.com.wallet.model.enums.Status;

import javax.validation.constraints.NotNull;

public record WalletFormPut(
        @NotNull(message = "'status' must be not null")
        Status status
) {
    public Wallet toWallet() {
        return new Wallet(status);
    }
}
