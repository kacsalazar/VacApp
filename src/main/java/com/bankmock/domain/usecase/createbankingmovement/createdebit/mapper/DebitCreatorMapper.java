package com.bankmock.domain.usecase.createbankingmovement.createdebit.mapper;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DebitCreatorMapper {

    public static BankingMovement buildMovementModel(DebitCreate movementInfo, BankAccount noSourceAccount,
                                                  String typeMovement){

        return BankingMovement.builder()
                .typeMovement(typeMovement)
                .customerAccountId(noSourceAccount.getId())
                .amount(movementInfo.getAmount())
                .token(movementInfo.getTargetTokenBass())
                .bank(movementInfo.getTargetBank())
                .status("IN_PROGRESS")
                .build();
    }
}
