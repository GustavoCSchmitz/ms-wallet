package br.com.wallet.api.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DepositForm(
        @NotBlank(message = "'walletId' must not be blank")
        String walletId,
        @NotNull(message = "'value' must not be blank")
        BigDecimal value,
        @NotBlank(message = "'senderUserId' must not be blank")
        String senderUserId
) {
}
