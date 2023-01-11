package br.com.wallet.model;

import br.com.wallet.model.enums.Status;
import br.com.wallet.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    private String id;
    private String userId;
    private BigDecimal accountBalance;
    private Status status;
    private LocalDateTime creationDate;
    private LocalDateTime updatingDate;
    private List<MovementHistory> extract;

    public Wallet(Status status) {
        this.status = status;
        this.updatingDate = DateUtils.getCurrentDate();
    }

    public Wallet(String id, BigDecimal accountBalance) {
        this.id = id;
        this.accountBalance = accountBalance;
        this.updatingDate = DateUtils.getCurrentDate();
    }
}
