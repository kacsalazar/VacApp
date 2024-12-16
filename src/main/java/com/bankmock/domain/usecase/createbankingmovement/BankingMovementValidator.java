package com.bankmock.domain.usecase.createbankingmovement;

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
    private final CreditMovement creditMovement;
    private final DebitMovement debitMovement;
    private final IBankingMovementGateway iBankingMovementGateway;

    private void isAccountValid(BankingMovementEntityRequest bankingMovementEntityRequest, Long idAccount){

        BankAccount account = this.iBankAccountGateway.findAccountById(idAccount);
        validAccount(account,bankingMovementEntityRequest.getDataInfo().getMovementInfo().getAmount() );
        //debitAccount(bankingMovementEntityRequest.getDataInfo().getMovementInfo().getAmount(), account);
    }

    public BankAccount validateMovement(BankingMovementEntityRequest movement, String commercialAlly){

        TokenAccount sourceAccount = this.
                validateToken(movement.getDataInfo().getCustomerData().getTokenBaas(), commercialAlly);
        BankAccount bankAccount = this.iBankAccountGateway.findAccountById(sourceAccount.getIdAccount());

        this.isAccountValid(movement, sourceAccount.getIdAccount());
        this.debitMovement.debitAccount(movement.getDataInfo().getMovementInfo().getAmount(),
                bankAccount);

        return bankAccount;

    }

    public void identifyBank(BankingMovementEntityRequest bmEntityRequest,
                             Long idSourceAccount, Long idMovement){
        String bank = "BANCO_A";
        if(bank.equals(bmEntityRequest.getDataInfo().getClientData().getBank())){
            this.validateSameBank(bmEntityRequest.getDataInfo().getClientData().getTokenBaas(),
                    bmEntityRequest.getDataInfo().getMovementInfo().getAmount(), idMovement);

        }else {
            this.validateDifferentBank(
                    bmEntityRequest.getDataInfo().getMovementInfo().getAmount(),
                    idSourceAccount, idMovement);
        }
    }

    private void validAccount(BankAccount account, BigDecimal amount){

        if (account==null) {
            throw new AppException(ConstantException.NONEXISTENT_ACCOUNT);
        }
        if (!account.getIsActive()){
            throw new AppException(ConstantException.INVALID_ACCOUNT);
        }
        if (account.getAmount().compareTo(amount) < 1) {
            throw new AppException(ConstantException.INSUFFICIENT_AMOUNT);
        }
    }

    private TokenAccount validateToken(String token, String commercialAlly){
        TokenAccount completeToken = this.iTokenGateway.findByTokenCommercialAlly(token, commercialAlly);
        if (completeToken == null) throw new AppException(ConstantException.INVALID_TOKEN);
        return completeToken;
    }



    private void validateSameBank(String tokenBaas, BigDecimal amount, Long idMovement){

            Token token = this.iTokenGateway.findByToken(tokenBaas);
            BankAccount bankAccount = this.iBankAccountGateway.findAccountById(token.getIdAccount());
            this.creditMovement.creditAccount(amount, bankAccount);
            BankingMovement movement = this.iBankingMovementGateway.findMovementById(idMovement);
            log.info("BANCO EN VALIDATE " + movement);
            movement.setStatus("SUCCESSFUL");
            this.iBankingMovementGateway.updateBankingMovement(movement);
            System.out.println("Transfer was made to the same bank account");

    }

    private void validateDifferentBank(BigDecimal amount,Long idSourceAccount, Long idMovement){
        BankingMovement movement = this.iBankingMovementGateway.findMovementById(idMovement);
        Boolean isCorrectNotify = this.iExternalBankConsumer.notifyBank();
        if(!isCorrectNotify){
            BankAccount bankAccount = this.iBankAccountGateway.findAccountById(idSourceAccount);
            this.creditMovement.creditAccount(amount, bankAccount);
            movement.setStatus("FAILED");
            this.iBankingMovementGateway.updateBankingMovement(movement);
            throw new AppException(ConstantException.PAYMENT_FAILED);
        }
        movement.setStatus("SUCCESSFUL");
        this.iBankingMovementGateway.updateBankingMovement(movement);
    }

}
