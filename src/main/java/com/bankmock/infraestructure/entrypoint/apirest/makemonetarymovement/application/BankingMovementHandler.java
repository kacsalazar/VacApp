package com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.application;

import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.domain.usecase.createbankingmovement.createdebit.DebitCreator;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementRequest;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain
        .BankingMovementResponse.buildSuccessResponse;

@Slf4j
@Component
@AllArgsConstructor
public class BankingMovementHandler {

    private final DebitCreator debitCreator;

    public BankingMovementResponse createMovement(BankingMovementRequest movementRequest, String commercialAlly){

        DebitCreate bankingMovement = BankingMovementMapper.toEntity(movementRequest);
        debitCreator.debit(bankingMovement, commercialAlly);

        return buildSuccessResponse(movementRequest.getMeta().getMessageId());
    }
}
