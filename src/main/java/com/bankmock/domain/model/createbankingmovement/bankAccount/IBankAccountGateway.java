package com.bankmock.domain.model.createbankingmovement.bankAccount;

import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankAccount.BankAccountData;

import java.math.BigDecimal;

public interface IBankAccountGateway {

    BankAccount findByNoAccount(String noAccount);
    void saveAmountAccount(BankAccount account);
    BankAccount findAccountById(Long id);
}
