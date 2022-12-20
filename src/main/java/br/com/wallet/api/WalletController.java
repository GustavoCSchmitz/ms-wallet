package br.com.wallet.api;

import br.com.wallet.api.contract.WalletApi;
import br.com.wallet.api.form.WalletForm;
import br.com.wallet.dto.WalletDto;
import br.com.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WalletController implements WalletApi {

    private final WalletService service;

    @Override
    public WalletDto createUser(@Valid WalletForm walletForm) {
        log.info("[USER - API] Creating user");
        return service.createWallet(walletForm);
    }
}
