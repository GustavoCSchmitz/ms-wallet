package br.com.wallet.dto;

import br.com.wallet.model.enums.Status;

public record UserResponseDto(
        String id,
        String name,
        Status status
) {
}
