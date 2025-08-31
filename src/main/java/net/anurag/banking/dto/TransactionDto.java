package net.anurag.banking.dto;

import java.time.LocalDateTime;

public record TransactionDto(Long Id , Long accountId , double ammount , String transactionType ,
                             LocalDateTime timestamp) {
}
