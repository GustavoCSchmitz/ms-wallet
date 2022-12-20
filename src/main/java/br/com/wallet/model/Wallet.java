package br.com.wallet.model;

import br.com.wallet.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    private String id;
    private String userId;
    private double accountBalance;
    private Status status;

}
