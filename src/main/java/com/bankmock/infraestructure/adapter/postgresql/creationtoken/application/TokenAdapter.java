package com.bankmock.infraestructure.adapter.postgresql.creationtoken.application;

import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.createtoken.Token;
import com.bankmock.domain.model.createtoken.TokenAccount;
import com.bankmock.infraestructure.adapter.postgresql.creationtoken.application.mapper.TokenMapper;
import com.bankmock.infraestructure.adapter.postgresql.creationtoken.infraestructure.TokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class TokenAdapter implements ITokenGateway {

    private final TokenRepository tokenRepository;

    @Override
    public TokenAccount findByTokenCommercialAlly(String token, String commercialAlly) {
        return TokenMapper.toEntity(this.tokenRepository.findByTokenCommercialAlly(token, commercialAlly));
    }

    @Override
    public Token findByToken(String token) {
        return TokenMapper.toEntity(this.tokenRepository.findByToken(token));
    }


}
