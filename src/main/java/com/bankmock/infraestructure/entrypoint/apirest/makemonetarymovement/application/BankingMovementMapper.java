package com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.application;

import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityResponse;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementRequest;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BankingMovementMapper {

    public static DebitCreate toEntity(BankingMovementRequest movementRequest){

        DebitCreate.CustomerInfo customerInfo = DebitCreate.CustomerInfo
                .builder()
                .docNumber(movementRequest.getDataInfo().getCustomerData().getCustomerInfo().getDocNumber())
                .docType(movementRequest.getDataInfo().getCustomerData().getCustomerInfo().getDocType())
                .build();

        DebitCreate.CustomerData customerData = DebitCreate.CustomerData
                .builder()
                .tokenBaas(movementRequest.getDataInfo().getCustomerData().getTokenBaas())
                .customerInfo(customerInfo)
                .build();

        DebitCreate.ClientData clientData = DebitCreate.ClientData
                .builder()
                .bank(movementRequest.getDataInfo().getClientData().getBank())
                .customerInfo(customerInfo)
                .tokenBaas(movementRequest.getDataInfo().getClientData().getTokenBaas())
                .build();

        DebitCreate.MovementInfo movementInfo = DebitCreate.MovementInfo
                .builder()
                .amount(movementRequest.getDataInfo().getMovementInfo().getAmount())
                .build();

        DebitCreate.Meta meta = DebitCreate.Meta.builder()
                .messageId(movementRequest.getMeta().getMessageId())
                .date(movementRequest.getMeta().getDate())
                .build();

        DebitCreate.DataInfo dataInfo = DebitCreate.DataInfo.builder()
                .customerData(customerData)
                .movementInfo(movementInfo)
                .clientData(clientData)
                .build();

        DebitCreate entity = DebitCreate.builder()
                .dataInfo(dataInfo)
                .meta(meta)
                .build();

        return entity;
    }



    public static BankingMovementResponse toResponse (BankingMovementEntityResponse bmEntityResponse){

        BankingMovementResponse.Meta meta = BankingMovementResponse.Meta.builder()
                .messageId(bmEntityResponse.getMeta().getMessageId())
                .date(bmEntityResponse.getMeta().getDate())
                .build();

        BankingMovementResponse response = BankingMovementResponse.builder()
                .meta(meta)
                .build();

        return response;
    }

}
