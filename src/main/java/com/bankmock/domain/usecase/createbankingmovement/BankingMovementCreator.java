package com.bankmock.domain.usecase.createbankingmovement;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityRequest;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityResponse;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.IBankingMovementGateway;
import com.bankmock.domain.model.createtoken.TokenAccount;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class BankingMovementCreator {

    private final IBankingMovementGateway iBankingMovementGateway;
    private final BankingMovementValidator bankingMovementValidator;


    private static final Logger logger = LoggerFactory.getLogger(BankingMovementCreator.class);

    public BankingMovementEntityResponse createMovement (BankingMovementEntityRequest movement, String commercialAlly){

        BankAccount bankAccount = this.bankingMovementValidator.validateMovement(movement, commercialAlly);

        BankingMovement movementCustomer = this.getAccountsToMovement(movement,
                bankAccount, "Debit");
        BankingMovement bankingMovement = this.iBankingMovementGateway.createMovement(movementCustomer);

        log.info("BANCO EN CREATOR " + bankingMovement);
        this.bankingMovementValidator.identifyBank(movement, bankAccount.getId(), bankingMovement.getId());

        return this.createResponse(movement.getMeta().getMessageId());
    }

    private BankingMovement getAccountsToMovement(BankingMovementEntityRequest movementInfo, BankAccount noSourceAccount, String typeMovement){

        logger.info("Se inicia el proceso de crear moviemiento");

        BankingMovement movement = BankingMovement.builder()
                .typeMovement(typeMovement)
                .customerAccountId(noSourceAccount.getId())
                .amount(movementInfo.getDataInfo().getMovementInfo().getAmount())
                .token(movementInfo.getDataInfo().getClientData().getTokenBaas())
                .bank(movementInfo.getDataInfo().getClientData().getBank())
                .status("IN_PROGRESS")
                .build();

        return movement;
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
