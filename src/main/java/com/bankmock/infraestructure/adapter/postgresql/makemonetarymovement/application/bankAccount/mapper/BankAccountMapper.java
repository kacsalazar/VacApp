package com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.application.bankAccount.mapper;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createtoken.Token;
import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankAccount.BankAccountData;
import org.springframework.beans.BeanUtils;

public class BankAccountMapper {

    public static BankAccount toEntity (BankAccountData accountData){
        BankAccount entity = new BankAccount();
        BeanUtils.copyProperties(accountData, entity);
        return entity;
    }

    public static BankAccountData toData(BankAccount account){
        BankAccountData data = new BankAccountData();
        BeanUtils.copyProperties(account, data);
        return data;
    }

}
