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
public class CreditMovement {

    private final IBankAccountGateway iBankAccountGateway;

    public void creditAccount(BigDecimal amount, BankAccount account){
        account.setAmount(account.getAmount().add(amount));
        this.iBankAccountGateway.saveAmountAccount(account);
    }
}
