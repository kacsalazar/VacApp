package com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.application;

import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityRequest;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityResponse;
import com.bankmock.domain.usecase.createbankingmovement.BankingMovementCreator;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementRequest;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class BankingMovementHandler {

    private final BankingMovementCreator bankingMovementCreator;

    public BankingMovementResponse createMovement(BankingMovementRequest movementRequest, String commercialAlly){

        BankingMovementEntityRequest bankingMovement = BankingMovementMapper.toEntity(movementRequest);
        log.info("OBJETO OMPLETO HANDLER" + bankingMovement);
        BankingMovementEntityResponse bmEntityResponse = this.bankingMovementCreator.createMovement(bankingMovement, commercialAlly);

        return BankingMovementMapper.toResponse(bmEntityResponse);
    }
}
