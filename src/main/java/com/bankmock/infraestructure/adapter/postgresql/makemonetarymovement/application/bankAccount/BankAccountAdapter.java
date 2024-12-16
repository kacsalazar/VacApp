package com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.application.bankAccount;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.application.bankAccount.mapper.BankAccountMapper;
import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankAccount.BankAccountData;
import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.infraestructure.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@AllArgsConstructor
public class BankAccountAdapter implements IBankAccountGateway {

    private final BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount findByNoAccount(String noAccount) {

        log.info("Numero Auenta" + noAccount );
        BankAccountData accountData = this.bankAccountRepository.findByNoAccount(noAccount);
        return BankAccountMapper.toEntity(accountData);
    }

    @Override
    public void saveAmountAccount(BankAccount account) {
        this.bankAccountRepository.save(BankAccountMapper.toData(account));
    }

    @Override
    public BankAccount findAccountById(Long id) {
        return BankAccountMapper.toEntity(this.bankAccountRepository.findById(id).get());
    }
}
