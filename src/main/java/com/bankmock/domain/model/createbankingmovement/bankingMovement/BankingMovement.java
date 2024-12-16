package com.bankmock.domain.model.createbankingmovement.bankingMovement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BankingMovement {

    private Long id;
    private String typeMovement;
    private Long customerAccountId;
    private BigDecimal amount;
    private String token;
    private String bank;
    private String status;

}
