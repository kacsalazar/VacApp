package com.bankmock.domain.usecase.createbankingmovement.createdebit;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.createtoken.TokenAccount;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.model.shared.exception.ConstantException;
import com.bankmock.domain.usecase.createbankingmovement.shared.accountretrievebytoken.AccountRetrieveByToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor
public class DebitValidator {

    private final AccountRetrieveByToken accountRetrieveByToken;

    public BankAccount validateMovementAndGetAccount(DebitCreate movement, String businessPartner){

        BankAccount bankAccount = accountRetrieveByToken.retrieve(movement.getSourceTokenBass(),
                businessPartner);
        validateAccount(movement.getAmount(), bankAccount);

        return bankAccount;
    }

    private void validateAccount(BigDecimal amountToDebit, BankAccount account){

        if (account==null) {
            throw new AppException(ConstantException.NONEXISTENT_ACCOUNT);
        }
        if (account.getIsActive().equals(Boolean.FALSE)){
            throw new AppException(ConstantException.INVALID_ACCOUNT);
        }
        if (account.getAmount().compareTo(amountToDebit) < 1) {
            throw new AppException(ConstantException.INSUFFICIENT_AMOUNT);
        }
        //debitAccount(bankingMovementEntityRequest.getDataInfo().getMovementInfo().getAmount(), account);
    }

}
