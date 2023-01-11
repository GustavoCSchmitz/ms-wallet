package br.com.wallet.dto;

import br.com.wallet.model.MovementHistory;
import br.com.wallet.model.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record MovementHistoryDto(
        String id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
        LocalDateTime operationDate,
        OperationType operation,
        BigDecimal value
) {

    public MovementHistoryDto(MovementHistory movementHistory){
        this(
                movementHistory.getId(),
                movementHistory.getOperationDate(),
                movementHistory.getOperation(),
                movementHistory.getValue()
        );
    }

    public static List<MovementHistoryDto> convert(List<MovementHistory> extract) {
        return extract.stream()
                .map(MovementHistoryDto::new)
                .toList();
    }
}
