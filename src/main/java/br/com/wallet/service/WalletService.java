package br.com.wallet.service;

import br.com.wallet.api.form.WalletForm;
import br.com.wallet.api.form.WalletFormPut;
import br.com.wallet.dto.WalletDto;
import br.com.wallet.model.Wallet;
import br.com.wallet.repository.WalletRepository;
import br.com.wallet.util.CopyPropertiesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

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

    public List<WalletDto> getAllWallets() {
        List<Wallet> wallets = getAllWalletsList();
        List<WalletDto> walletDtoList = WalletDto.convert(wallets);
        log.info("Wallet list returned");

        return walletDtoList;
    }

    public WalletDto getWalletById(String id) {
        return repository.findById(id)
                .map(this::getWalletDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public WalletDto getWalletByUserId(String id) {
        Wallet wallet = repository.findByUserId(id);
        WalletDto walletDto = new WalletDto(wallet);
        log.info("Wallet returned");

        return walletDto;
    }

    @Transactional
    public WalletDto updateWallet(String id, WalletFormPut walletFormPut) {
        Wallet wallet = findWalletById(id);
        Wallet updatedWallet = updateWallet(walletFormPut, wallet);

        WalletDto walletDto = new WalletDto(updatedWallet);
        log.info("Wallet updated successfully");

        return walletDto;
    }

    private Wallet updateWallet(WalletFormPut walletFormPut, Wallet wallet) {
        try{
            Wallet updatedWallet = walletFormPut.toWallet();
            CopyPropertiesUtils.copyFieldsNotNull(wallet,updatedWallet);
            return saveWallet(wallet);
        }catch (Exception e){
            log.error("Cannot update wallet");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private Wallet findWalletById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private WalletDto getWalletDTO(Wallet wallet) {
        WalletDto walletDto = new WalletDto(wallet);
        log.info("Wallet returned");
        return walletDto;
    }

    private List<Wallet> getAllWalletsList() {
        return repository.findAll();
    }

    private Wallet saveWallet(Wallet wallet) {
        return repository.save(wallet);
    }
}
