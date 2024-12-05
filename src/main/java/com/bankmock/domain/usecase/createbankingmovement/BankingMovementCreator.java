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
    private final IBankAccountGateway iBankAccountGateway;

    private static final Logger logger = LoggerFactory.getLogger(BankingMovementCreator.class);

    public BankingMovementEntityResponse createMovement (BankingMovementEntityRequest movement, String commercialAlly){

        TokenAccount sourceAccount = this.bankingMovementValidator.
                validateToken(movement.getDataInfo().getCustomerData().getTokenBaas(), commercialAlly);
        BankAccount bankAccount = this.iBankAccountGateway.findAccountById(sourceAccount.getIdAccount());

        this.bankingMovementValidator.isAccountValid(movement, sourceAccount.getIdAccount());
        this.bankingMovementValidator.debitAccount(movement.getDataInfo().getMovementInfo().getAmount(),
                bankAccount);
        //dato mockeado indicando
        this.bankingMovementValidator.validateBank(movement, bankAccount.getId());

        BankingMovement movementCustomer = this.getAccountsToMovement(movement,
                bankAccount, "Debit");
        this.iBankingMovementGateway.createMovement(movementCustomer);

        BankingMovementEntityResponse.Meta meta = BankingMovementEntityResponse.Meta.builder()
                .messageId(movement.getMeta().getMessageId())
                .date(new Date())
                .build();

        return  BankingMovementEntityResponse.builder()
                .meta(meta)
                .build();

    }

    public BankingMovement getAccountsToMovement(BankingMovementEntityRequest movementInfo, BankAccount noSourceAccount, String typeMovement){

        logger.info("Se inicia el proceso de crear moviemiento");

        BankingMovement movement = new BankingMovement();
        movement.setTypeMovement(typeMovement);
        movement.setCustomerAccountId(noSourceAccount.getId());
        movement.setAmount(movementInfo.getDataInfo().getMovementInfo().getAmount());
        movement.setToken(movementInfo.getDataInfo().getClientData().getTokenBaas());
        movement.setBank(movementInfo.getDataInfo().getClientData().getBank());

        return movement;
    }

}
