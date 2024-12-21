package com.bankmock.domain.usecase.createbankingmovement.creditcreate;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.usecase.createbankingmovement.shared.accountretrievebytoken.AccountRetrieveByToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor
public class CreditCreator {

    private final AccountRetrieveByToken accountRetrieveByToken;

    public Boolean byToken(String tokenBusinessPartner, BigDecimal amount){
        return true;
    }

    public Boolean byAccount(BankAccount bankAccount, BigDecimal amount){
        return true;
    }
}
