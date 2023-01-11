package br.com.wallet.model;

import br.com.wallet.model.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class MovementHistory {

    @Id
    private String id;
    private LocalDateTime operationDate;
    private OperationType operation;
    private BigDecimal value;

}
