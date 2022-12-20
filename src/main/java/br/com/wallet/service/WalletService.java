package br.com.wallet.service;

import br.com.wallet.api.form.WalletForm;
import br.com.wallet.dto.WalletDto;
import br.com.wallet.model.Wallet;
import br.com.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository repository;

    @Transactional
    public WalletDto createWallet(@Valid WalletForm form){
        try {
            Wallet wallet = form.toWallet(form);
            Wallet savedWallet = saveWallet(wallet);
            WalletDto walletDto = new WalletDto(savedWallet);
            log.info("Wallet create successfully");
            return walletDto;
        }catch (Exception e){
            log.error("Cannot create wallet");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private Wallet saveWallet(Wallet wallet) {
        return repository.save(wallet);
    }
}
