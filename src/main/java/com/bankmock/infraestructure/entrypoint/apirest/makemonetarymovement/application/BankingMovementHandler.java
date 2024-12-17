package com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.application;

import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityRequest;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityResponse;
import com.bankmock.domain.usecase.createbankingmovement.createdebit.DebitCreator;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementRequest;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class BankingMovementHandler {

    private final DebitCreator debitCreator;

    public BankingMovementResponse createMovement(BankingMovementRequest movementRequest, String commercialAlly){

        BankingMovementEntityRequest bankingMovement = BankingMovementMapper.toEntity(movementRequest);
        BankingMovementEntityResponse bmEntityResponse = this.debitCreator.createMovement(bankingMovement, commercialAlly);

        return BankingMovementMapper.toResponse(bmEntityResponse);
    }
}
