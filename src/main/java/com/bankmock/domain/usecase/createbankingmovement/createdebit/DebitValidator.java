package com.bankmock.domain.usecase.createbankingmovement.createdebit;

import com.bankmock.domain.model.createbankingmovement.IExternalBankConsumer;
import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityRequest;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.IBankingMovementGateway;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.createtoken.Token;
import com.bankmock.domain.model.createtoken.TokenAccount;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.model.shared.exception.ConstantException;
import com.bankmock.domain.usecase.createbankingmovement.CreditMovement;
import com.bankmock.domain.usecase.createbankingmovement.DebitMovement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor
public class DebitValidator {

    private final IBankAccountGateway iBankAccountGateway;
    private final ITokenGateway iTokenGateway;
    private final IExternalBankConsumer iExternalBankConsumer;
    private final CreditMovement creditMovement;
    private final DebitMovement debitMovement;

    public BankAccount validateMovement(BankingMovementEntityRequest movement, String commercialAlly){

        TokenAccount sourceAccount = this.
                getAccountByToken(movement.getDataInfo().getCustomerData().getTokenBaas(), commercialAlly);
        BankAccount bankAccount = this.iBankAccountGateway.findAccountById(sourceAccount.getIdAccount());

        this.isAccountValid(movement, sourceAccount.getIdAccount());
        this.debitMovement.debitAccount(movement.getDataInfo().getMovementInfo().getAmount(),
                bankAccount);

        return bankAccount;

    }



    private void isAccountValid(BankingMovementEntityRequest bankingMovementEntityRequest, Long idAccount){

        BankAccount account = this.iBankAccountGateway.findAccountById(idAccount);
        validAccount(account,bankingMovementEntityRequest.getDataInfo().getMovementInfo().getAmount() );
        //debitAccount(bankingMovementEntityRequest.getDataInfo().getMovementInfo().getAmount(), account);
    }

    private void validAccount(BankAccount account, BigDecimal amount){

        if (account==null) {
            throw new AppException(ConstantException.NONEXISTENT_ACCOUNT);
        }
        if (account.getIsActive().equals(Boolean.FALSE)){
            throw new AppException(ConstantException.INVALID_ACCOUNT);
        }
        if (account.getAmount().compareTo(amount) < 1) {
            throw new AppException(ConstantException.INSUFFICIENT_AMOUNT);
        }
    }

    private TokenAccount getAccountByToken(String token, String commercialAlly){
        TokenAccount completeToken = this.iTokenGateway.findByTokenCommercialAlly(token, commercialAlly);
        if (completeToken == null) throw new AppException(ConstantException.INVALID_TOKEN);
        return completeToken;
    }

}
