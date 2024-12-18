package com.bankmock.domain.usecase.createbankingmovement.createdebit;

import com.bankmock.domain.model.createbankingmovement.IExternalBankConsumer;
import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityResponse;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.IBankingMovementGateway;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.createtoken.Token;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.model.shared.exception.ConstantException;
import com.bankmock.domain.usecase.createbankingmovement.CreditMovement;
import com.bankmock.domain.usecase.createbankingmovement.createdebit.mapper.DebitCreatorMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static com.bankmock.domain.usecase.createbankingmovement.createdebit.mapper.DebitCreatorMapper.buildMovementModel;

@Service
@Log4j2
@AllArgsConstructor
public class DebitCreator {

    private final IExternalBankConsumer iExternalBankConsumer;
    private final CreditMovement creditMovement;
    private final IBankingMovementGateway iBankingMovementGateway;
    private final DebitValidator debitValidator;
    private final IBankAccountGateway iBankAccountGateway;
    private final ITokenGateway iTokenGateway;

    public void createMovement (DebitCreate movement, String commercialAlly){

        BankAccount bankAccount = debitValidator.validateMovement(movement, commercialAlly);
        // TODO: 17/12/24 Recuperar cuenta de cliente acá, quitarlo de debir validator
        debitAccount(movement.getDataInfo().getMovementInfo().getAmount(),
                bankAccount);
        BankingMovement movementCustomer = buildMovementModel(movement,
                bankAccount, "Debit");
        // TODO: 18/12/24 Recuperar y moven entre dependencias solo información necesaria.
        String idRegistreredMovement = iBankingMovementGateway.createMovement(movementCustomer);

        log.info("BANCO EN CREATOR " + idRegistreredMovement);
        identifyBankOfCreditUser(movement, bankAccount.getId(), idRegistreredMovement);
    }

    private void debitAccount(BigDecimal amount, BankAccount account){
        account.setAmount(account.getAmount().subtract(amount));
        this.iBankAccountGateway.saveAmountAccount(account);
    }

    private void identifyBankOfCreditUser(DebitCreate bmEntityRequest,
                                          Long idSourceAccount, Long idMovement){
        String bank = "BANCO_A";
        if(bank.equals(bmEntityRequest.getDataInfo().getClientData().getBank())){
            creditToCustomer(bmEntityRequest.getDataInfo().getClientData().getTokenBaas(),
                    bmEntityRequest.getDataInfo().getMovementInfo().getAmount(), idMovement);

        }else {
            notifyCreditToDifferentBank(
                    bmEntityRequest.getDataInfo().getMovementInfo().getAmount(),
                    idSourceAccount, idMovement);
        }
    }

    private void creditToCustomer(String tokenBaas, BigDecimal amount, Long idMovement){

        Token token = this.iTokenGateway.findByToken(tokenBaas);
        BankAccount bankAccount = this.iBankAccountGateway.findAccountById(token.getIdAccount());
        this.creditMovement.creditAccount(amount, bankAccount);
        BankingMovement movement = this.iBankingMovementGateway.findMovementById(idMovement);
        log.info("BANCO EN VALIDATE " + movement);
        movement.setStatus("SUCCESSFUL");
        this.iBankingMovementGateway.updateBankingMovement(movement);
        System.out.println("Transfer was made to the same bank account");

    }

    private void notifyCreditToDifferentBank(BigDecimal amount, Long idSourceAccount, Long idMovement){
        BankingMovement movement = this.iBankingMovementGateway.findMovementById(idMovement);
        Boolean isNotificationSuccess = this.iExternalBankConsumer.notifyBank();
        if(isNotificationSuccess.equals(Boolean.FALSE)){
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
