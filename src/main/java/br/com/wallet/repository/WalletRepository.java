package br.com.wallet.repository;

import br.com.wallet.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {

    @Query("{'userId': ?0}")
    Wallet findByUserId(String userId);
}
