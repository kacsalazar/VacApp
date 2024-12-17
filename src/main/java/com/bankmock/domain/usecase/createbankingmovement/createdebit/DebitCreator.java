package com.bankmock.domain.usecase.createbankingmovement.createdebit;

import com.bankmock.domain.model.createbankingmovement.IExternalBankConsumer;
import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityRequest;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityResponse;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.IBankingMovementGateway;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.createtoken.Token;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.model.shared.exception.ConstantException;
import com.bankmock.domain.usecase.createbankingmovement.CreditMovement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class DebitCreator {

    private final IExternalBankConsumer iExternalBankConsumer;
    private final CreditMovement creditMovement;
    private final IBankingMovementGateway iBankingMovementGateway;
    private final DebitValidator debitValidator;
    private final IBankAccountGateway iBankAccountGateway;
    private final ITokenGateway iTokenGateway;

    private static final Logger logger = LoggerFactory.getLogger(DebitCreator.class);

    public BankingMovementEntityResponse createMovement (BankingMovementEntityRequest movement, String commercialAlly){

        BankAccount bankAccount = this.debitValidator.validateMovement(movement, commercialAlly);

        BankingMovement movementCustomer = this.getAccountsToMovement(movement,
                bankAccount, "Debit");
        BankingMovement bankingMovement = this.iBankingMovementGateway.createMovement(movementCustomer);

        log.info("BANCO EN CREATOR " + bankingMovement);
        identifyBankOfCreditUser(movement, bankAccount.getId(), bankingMovement.getId());

        return this.createResponse(movement.getMeta().getMessageId());
    }

    public void identifyBankOfCreditUser(BankingMovementEntityRequest bmEntityRequest,
                                         Long idSourceAccount, Long idMovement){
        String bank = "BANCO_A";
        if(bank.equals(bmEntityRequest.getDataInfo().getClientData().getBank())){
            this.creditToCustomer(bmEntityRequest.getDataInfo().getClientData().getTokenBaas(),
                    bmEntityRequest.getDataInfo().getMovementInfo().getAmount(), idMovement);

        }else {
            this.validateDifferentBank(
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

    private BankingMovement getAccountsToMovement(BankingMovementEntityRequest movementInfo, BankAccount noSourceAccount,
                                                  String typeMovement){

        logger.info("Se inicia el proceso de crear moviemiento");

        return BankingMovement.builder()
                .typeMovement(typeMovement)
                .customerAccountId(noSourceAccount.getId())
                .amount(movementInfo.getDataInfo().getMovementInfo().getAmount())
                .token(movementInfo.getDataInfo().getClientData().getTokenBaas())
                .bank(movementInfo.getDataInfo().getClientData().getBank())
                .status("IN_PROGRESS")
                .build();
    }

    private BankingMovementEntityResponse createResponse(String messageId){

        BankingMovementEntityResponse.Meta meta = BankingMovementEntityResponse.Meta.builder()
                .messageId(messageId)
                .date(new Date())
                .build();

        return  BankingMovementEntityResponse.builder()
                .meta(meta)
                .build();
    }

}
