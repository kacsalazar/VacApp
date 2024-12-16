package com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.application.bankingMovement.mapper;

import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankAccount.BankAccountData;
import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankingMovement.BankingMovementData;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementResponse;
import org.springframework.beans.BeanUtils;

public class BankingMovementMapper {

    public static BankingMovementData toData (BankingMovement movement){

        BankingMovementData data = BankingMovementData.builder()
                .id(movement.getId())
                .typeMovement(movement.getTypeMovement())
                .customerAccountId(movement.getCustomerAccountId())
                .amount(movement.getAmount())
                .token(movement.getToken())
                .bank(movement.getBank())
                .status(movement.getStatus())
                .build();

        return data;
    }

    public  static BankingMovement toEntity (BankingMovementData movementData){

        BankingMovement data = BankingMovement.builder()
                .id(movementData.getId())
                .typeMovement(movementData.getTypeMovement())
                .customerAccountId(movementData.getCustomerAccountId())
                .amount(movementData.getAmount())
                .token(movementData.getToken())
                .bank(movementData.getBank())
                .build();

        return data;
    }
}
