package com.bankmock.domain.usecase.createbankingmovement;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor
public class DebitMovement {

    private final IBankAccountGateway iBankAccountGateway;

    public void debitAccount(BigDecimal amount, BankAccount account){
        account.setAmount(account.getAmount().subtract(amount));
        this.iBankAccountGateway.saveAmountAccount(account);
    }
}
