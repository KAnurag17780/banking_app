package net.anurag.banking.dto;

public record TransferFundDto(Long fromAccountId,
                              Long toAccountId,
                              double ammount) {
}
