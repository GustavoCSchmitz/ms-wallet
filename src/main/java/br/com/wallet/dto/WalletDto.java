package br.com.wallet.dto;

import br.com.wallet.model.Wallet;
import br.com.wallet.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record WalletDto(
        String id,
        String userId,
        BigDecimal accountBalance,
        Status status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
        LocalDateTime creationDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
        LocalDateTime updatingDate,
        List<MovementHistoryDto> extract
) {
    public WalletDto(Wallet wallet) {
        this(
                wallet.getId(),
                wallet.getUserId(),
                wallet.getAccountBalance(),
                wallet.getStatus(),
                wallet.getCreationDate(),
                wallet.getUpdatingDate(),
                MovementHistoryDto.convert(wallet.getExtract())
        );
    }

    public static List<WalletDto> convert(List<Wallet> wallets) {
        return wallets.stream()
                .map(WalletDto::new)
                .toList();
    }
}
