package br.com.wallet.repository;

import br.com.wallet.model.MovementHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementHistoryRepository extends MongoRepository<MovementHistory, String> {

}
