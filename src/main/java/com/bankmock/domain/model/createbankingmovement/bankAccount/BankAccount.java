package com.bankmock.domain.model.createbankingmovement.bankAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BankAccount {

    private Long id;
    private String noAccount;
    private BigDecimal amount;
    private String dniCustomer;
    private Boolean isActive;
    private String typeAccount;
}
