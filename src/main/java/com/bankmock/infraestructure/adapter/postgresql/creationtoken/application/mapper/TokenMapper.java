package com.bankmock.infraestructure.adapter.postgresql.creationtoken.application.mapper;

import com.bankmock.domain.model.createtoken.Token;
import com.bankmock.domain.model.createtoken.TokenAccount;
import com.bankmock.infraestructure.adapter.postgresql.creationtoken.domain.TokenAccountData;
import com.bankmock.infraestructure.adapter.postgresql.creationtoken.domain.TokenData;
import org.springframework.beans.BeanUtils;

public class TokenMapper {

    public static TokenAccount toEntity(TokenAccountData tokenData){
        TokenAccount entity = new TokenAccount();
        BeanUtils.copyProperties(tokenData, entity);
        return entity;
    }

    public static Token toEntity(TokenData tokenData){
        Token entity = new Token();
        BeanUtils.copyProperties(tokenData, entity);
        return entity;
    }
}
