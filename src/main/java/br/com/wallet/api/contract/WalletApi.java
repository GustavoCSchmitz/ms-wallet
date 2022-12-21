package br.com.wallet.api.contract;

import br.com.wallet.api.form.WalletForm;
import br.com.wallet.api.form.WalletFormPut;
import br.com.wallet.dto.WalletDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RequestMapping("/wallets")
public interface WalletApi {

    @Operation(summary = "Create a new wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "412", description = "Precondition failed", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Service error", content = @Content(mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    WalletDto createUser(@RequestBody WalletForm walletForm);

    @Operation(summary = "List all wallets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Service error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    List<WalletDto> getAllWallets();

    @Operation(summary = "Get a wallet by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Wallet not found", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    WalletDto getWallet(
            @Parameter(description = "wallet id")
            @PathVariable String id
    );

    @Operation(summary = "Get a wallet by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Wallet not found", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/user/{id}")
    WalletDto getWalletByUserId(
            @Parameter(description = "user id")
            @PathVariable String id
    );

    @Operation(summary = "Update a wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Wallet not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "412", description = "Precondition failed", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    WalletDto updateWallet(
            @Parameter(description = "wallet id")
            @PathVariable String id,
            @RequestBody WalletFormPut walletFormPut
    );

    @Operation(summary = "Delete a wallet by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Wallet not found", content = @Content(mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    ResponseEntity deleteWallet(
            @Parameter(description = "wallet id")
            @PathVariable String id
    );
}
