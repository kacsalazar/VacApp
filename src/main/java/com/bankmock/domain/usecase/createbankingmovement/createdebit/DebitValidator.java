package com.bankmock.domain.usecase.createbankingmovement.createdebit;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.createtoken.TokenAccount;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.model.shared.exception.ConstantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class DebitValidator {

    private final IBankAccountGateway iBankAccountGateway;
    private final ITokenGateway iTokenGateway;

    public Long validateMovement(DebitCreate movement, String commercialAlly){

        TokenAccount sourceAccount = this.
                getAccountByToken(movement.getSourceTokenBass(), commercialAlly);
        this.validateAccount(movement, sourceAccount.getIdAccount());

        return sourceAccount.getIdAccount();

    }

    private void validateAccount(DebitCreate debitCreate, Long idAccount){

        BankAccount account = this.iBankAccountGateway.findAccountById(idAccount);
        if (account==null) {
            throw new AppException(ConstantException.NONEXISTENT_ACCOUNT);
        }
        if (account.getIsActive().equals(Boolean.FALSE)){
            throw new AppException(ConstantException.INVALID_ACCOUNT);
        }
        if (account.getAmount().compareTo(debitCreate
                .getAmount()) < 1) {
            throw new AppException(ConstantException.INSUFFICIENT_AMOUNT);
        }
        //debitAccount(bankingMovementEntityRequest.getDataInfo().getMovementInfo().getAmount(), account);
    }

    private TokenAccount getAccountByToken(String token, String commercialAlly){
        TokenAccount completeToken = this.iTokenGateway.findByTokenCommercialAlly(token, commercialAlly);
        if (completeToken == null) throw new AppException(ConstantException.INVALID_TOKEN);
        return completeToken;
    }

}
