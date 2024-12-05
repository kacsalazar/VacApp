package com.bankmock.domain.usecase.createbankingmovement;

import com.bankmock.domain.model.createbankingmovement.IExternalBankConsumer;
import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityRequest;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.createtoken.Token;
import com.bankmock.domain.model.createtoken.TokenAccount;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.model.shared.exception.ConstantException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor
public class BankingMovementValidator {

    private final IBankAccountGateway iBankAccountGateway;
    private final ITokenGateway iTokenGateway;
    private final IExternalBankConsumer iExternalBankConsumer;

    public void isAccountValid(BankingMovementEntityRequest bankingMovementEntityRequest, Long idAccount){

        BankAccount account = this.iBankAccountGateway.findAccountById(idAccount);
        validAccount(account,bankingMovementEntityRequest.getDataInfo().getMovementInfo().getAmount() );
        debitAccount(bankingMovementEntityRequest.getDataInfo().getMovementInfo().getAmount(), account);
    }

    public void validAccount(BankAccount account, BigDecimal amount){

        if (account==null) throw new AppException(ConstantException.NONEXISTENT_ACCOUNT);
        if (!account.getIsActive()) throw new AppException(ConstantException.INVALID_ACCOUNT);
        if (account.getAmount().compareTo(amount) < 1) throw new AppException(ConstantException.INSUFFICIENT_AMOUNT);
    }

    public void debitAccount(BigDecimal amount, BankAccount account){
        account.setAmount(account.getAmount().subtract(amount));
        this.iBankAccountGateway.saveAmountAccount(account);
    }

    public TokenAccount validateToken(String token, String commercialAlly){
        TokenAccount completeToken = this.iTokenGateway.findByTokenCommercialAlly(token, commercialAlly);
        if (completeToken == null) throw new AppException(ConstantException.INVALID_TOKEN);
        return completeToken;
    }


    public void validateBank(BankingMovementEntityRequest bmEntityRequest,
                                Long idSourceAccount){

        String bank = "BANCO_A";
        if(bank.equals(bmEntityRequest.getDataInfo().getClientData().getBank())){

            /*Token token = this.iTokenGateway.findByToken(bmEntityRequest.getDataInfo().getClientData().getTokenBaas());
            BankAccount bankAccount = this.iBankAccountGateway.findAccountById(token.getIdAccount());
            creditAccount(bmEntityRequest.getDataInfo().getMovementInfo().getAmount(), bankAccount);
            */
            validateSameBank(bmEntityRequest.getDataInfo().getClientData().getTokenBaas(),
                    bmEntityRequest.getDataInfo().getMovementInfo().getAmount());

        }else{

            /*Boolean isCorrectNotify = this.iExternalBankConsumer.notifyBank();
            if(!isCorrectNotify){
                BankAccount bankAccount = this.iBankAccountGateway.findAccountById(idSourceAccount);
                creditAccount(bmEntityRequest.getDataInfo().getMovementInfo().getAmount(), bankAccount);
                throw new AppException(ConstantException.PAYMENT_FAILED);
            }*/

            validateDifferentBank(bmEntityRequest.getDataInfo().getMovementInfo().getAmount(), idSourceAccount);
        }
    }

    public void validateSameBank(String tokenBaas, BigDecimal amount){

            Token token = this.iTokenGateway.findByToken(tokenBaas);
            BankAccount bankAccount = this.iBankAccountGateway.findAccountById(token.getIdAccount());
            creditAccount(amount, bankAccount);
            System.out.println("Transfer was made to the same bank account");

    }

    public void validateDifferentBank(BigDecimal amount,Long idSourceAccount ){
        Boolean isCorrectNotify = this.iExternalBankConsumer.notifyBank();
        if(!isCorrectNotify){
            BankAccount bankAccount = this.iBankAccountGateway.findAccountById(idSourceAccount);
            creditAccount(amount, bankAccount);
            throw new AppException(ConstantException.PAYMENT_FAILED);
        }

    }

    public void creditAccount(BigDecimal amount, BankAccount account){
        account.setAmount(account.getAmount().add(amount));
        this.iBankAccountGateway.saveAmountAccount(account);
    }

}
