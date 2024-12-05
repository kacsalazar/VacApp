package com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.application;

import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityRequest;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovementEntityResponse;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementRequest;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementResponse;
import org.springframework.stereotype.Component;

@Component
public class BankingMovementMapper {

    public static BankingMovementEntityRequest toEntity(BankingMovementRequest movementRequest){

        BankingMovementEntityRequest.CustomerInfo customerInfo = BankingMovementEntityRequest.CustomerInfo
                .builder()
                .docNumber(movementRequest.getDataInfo().getCustomerData().getCustomerInfo().getDocNumber())
                .docType(movementRequest.getDataInfo().getCustomerData().getCustomerInfo().getDocType())
                .build();

        BankingMovementEntityRequest.CustomerData customerData = BankingMovementEntityRequest.CustomerData
                .builder()
                .tokenBaas(movementRequest.getDataInfo().getCustomerData().getTokenBaas())
                .customerInfo(customerInfo)
                .build();

        BankingMovementEntityRequest.ClientData clientData = BankingMovementEntityRequest.ClientData
                .builder()
                .bank(movementRequest.getDataInfo().getClientData().getBank())
                .customerInfo(customerInfo)
                .tokenBaas(movementRequest.getDataInfo().getClientData().getTokenBaas())
                .build();

        BankingMovementEntityRequest.MovementInfo movementInfo = BankingMovementEntityRequest.MovementInfo
                .builder()
                .amount(movementRequest.getDataInfo().getMovementInfo().getAmount())
                .build();

        BankingMovementEntityRequest.Meta meta = BankingMovementEntityRequest.Meta.builder()
                .messageId(movementRequest.getMeta().getMessageId())
                .date(movementRequest.getMeta().getDate())
                .build();

        BankingMovementEntityRequest.DataInfo dataInfo = BankingMovementEntityRequest.DataInfo.builder()
                .customerData(customerData)
                .movementInfo(movementInfo)
                .clientData(clientData)
                .build();

        BankingMovementEntityRequest entity = BankingMovementEntityRequest.builder()
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
