package br.com.wallet.api.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record WithdrawForm(
        @NotBlank(message = "'walletId' must not be blank")
        String walletId,
        @NotNull(message = "'value' must not be blank")
        BigDecimal value,
        @NotBlank(message = "'receiverUserId' must not be blank")
        String receiverUserId
) {
}
