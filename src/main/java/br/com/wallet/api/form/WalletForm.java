package br.com.wallet.api.form;

import br.com.wallet.model.Wallet;
import br.com.wallet.model.enums.Status;
import br.com.wallet.util.DateUtils;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record WalletForm(
        @NotBlank(message = "'userID' must not be blank")
        String userId
) {
    public Wallet toWallet(WalletForm form) {
        return Wallet.builder()
                .userId(form.userId())
                .accountBalance(BigDecimal.ZERO)
                .status(Status.ACTIVE)
                .creationDate(DateUtils.getCurrentDate())
                .build();
    }
}
