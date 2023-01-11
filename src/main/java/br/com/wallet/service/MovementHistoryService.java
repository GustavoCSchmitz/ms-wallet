package br.com.wallet.service;

import br.com.wallet.model.MovementHistory;
import br.com.wallet.model.enums.OperationType;
import br.com.wallet.repository.MovementHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementHistoryService {

    private final MovementHistoryRepository movementHistoryRepository;

    public void saveMovementHistory(String walletId, BigDecimal value, OperationType operation){
        MovementHistory movementHistory = MovementHistory.builder()
                .id(walletId)
                .operationDate(LocalDateTime.now())
                .operation(operation)
                .value(value)
                .build();
        save(movementHistory);
        log.info("Movement history has been saved");
    }

    private MovementHistory save(MovementHistory movementHistory){
        return movementHistoryRepository.save(movementHistory);
    }
}
