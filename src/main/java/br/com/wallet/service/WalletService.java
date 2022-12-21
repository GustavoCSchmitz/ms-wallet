package br.com.wallet.service;

import br.com.wallet.api.form.WalletForm;
import br.com.wallet.api.form.WalletFormPut;
import br.com.wallet.dto.WalletDto;
import br.com.wallet.exceptions.NotFoundException;
import br.com.wallet.exceptions.WalletException;
import br.com.wallet.model.Wallet;
import br.com.wallet.repository.WalletRepository;
import br.com.wallet.util.CopyPropertiesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private static final String WALLET_NOT_FOUND_MESSAGE = "Wallet not found";

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
            throw new WalletException(e.getMessage());
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
                .orElseThrow(this::handleNotFoundException);
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
            throw new WalletException(e.getMessage());
        }
    }

    private Wallet findWalletById(String id) {
        return repository.findById(id)
                .orElseThrow(this::handleNotFoundException);
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

    private NotFoundException handleNotFoundException() {
        log.error(WALLET_NOT_FOUND_MESSAGE);
        return new NotFoundException(WALLET_NOT_FOUND_MESSAGE);
    }

    public ResponseEntity deleteWallet(String id) {
        try {
            repository.deleteById(id);
            log.info("Wallet deleted successfully");
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            log.error("Cannot delete wallet");
            throw new WalletException(e.getMessage());
        }
    }
}
