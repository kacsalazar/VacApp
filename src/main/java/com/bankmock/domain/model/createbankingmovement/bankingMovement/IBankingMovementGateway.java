package com.bankmock.domain.model.createbankingmovement.bankingMovement;

import java.math.BigDecimal;

public interface IBankingMovementGateway {
    BankingMovement createMovement(BankingMovement movement);
    BankingMovement findMovementById(Long id);
    void updateBankingMovement(BankingMovement movement);
}
