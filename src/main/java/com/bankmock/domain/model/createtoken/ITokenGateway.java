package com.bankmock.domain.model.createtoken;

public interface ITokenGateway {

    TokenAccount findByTokenCommercialAlly(String token, String commercialAlly);
    Token findByToken(String token);


}

