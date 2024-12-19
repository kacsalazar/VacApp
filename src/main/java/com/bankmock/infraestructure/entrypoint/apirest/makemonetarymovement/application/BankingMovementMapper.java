package com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.application;

import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BankingMovementMapper {

    public static DebitCreate toEntity(BankingMovementRequest movementRequest){

        DebitCreate entity = DebitCreate
                .builder()
                .sourceTokenBass(movementRequest.getDataInfo().getCustomerData().getTokenBaas())
                .sourceDocNumber(movementRequest.getDataInfo().getCustomerData().getCustomerInfo().getDocNumber())
                .targetBank(movementRequest.getDataInfo().getClientData().getBank())
                .targetDocNumber(movementRequest.getDataInfo().getClientData().getCustomerInfo().getDocNumber())
                .targetTokenBass(movementRequest.getDataInfo().getClientData().getTokenBaas())
                .amount(movementRequest.getDataInfo().getMovementInfo().getAmount())
                .build();


        return entity;
    }

}
