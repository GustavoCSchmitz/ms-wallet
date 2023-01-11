package br.com.wallet.service;

import br.com.wallet.api.form.DepositForm;
import br.com.wallet.api.form.WalletForm;
import br.com.wallet.api.form.WalletFormPut;
import br.com.wallet.dto.UserResponseDto;
import br.com.wallet.dto.WalletDto;
import br.com.wallet.dto.WalletResponseDto;
import br.com.wallet.exceptions.NotFoundException;
import br.com.wallet.exceptions.WalletException;
import br.com.wallet.model.Wallet;
import br.com.wallet.model.enums.OperationType;
import br.com.wallet.model.enums.Status;
import br.com.wallet.repository.WalletRepository;
import br.com.wallet.util.CopyPropertiesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private static final String WALLET_NOT_FOUND_MESSAGE = "Wallet not found";

    private final UserService userService;

    private final MovementHistoryService movementHistoryService;

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

    public WalletResponseDto deposit(DepositForm depositForm) {
        Wallet wallet = findWalletById(depositForm.walletId());
        UserResponseDto user = userService.getUser(depositForm.senderUserId());
        if(isValidOperation(wallet, user)){
            BigDecimal newAccountBalance = calculateAccountBalance(depositForm.value(), wallet.getAccountBalance());
            Wallet updatedWallet = new Wallet(depositForm.walletId(), newAccountBalance);
            CopyPropertiesUtils.copyFieldsNotNull(wallet,updatedWallet);
            Wallet savedWallet = saveWallet(wallet);
            movementHistoryService.saveMovementHistory(savedWallet.getId(), depositForm.value(), OperationType.DEPOSIT);
            log.info("Deposit made successfully");
            return new WalletResponseDto(savedWallet.getId(), newAccountBalance);
        }
        return null;
    }

    private boolean isValidOperation(Wallet wallet, UserResponseDto user) {
        return Objects.equals(wallet.getStatus(), Status.ACTIVE)
                && nonNull(user) && Objects.equals(user.status(), Status.ACTIVE);
    }

    private BigDecimal calculateAccountBalance(BigDecimal value, BigDecimal accountBalance) {
        return value.add(accountBalance);
    }

}
