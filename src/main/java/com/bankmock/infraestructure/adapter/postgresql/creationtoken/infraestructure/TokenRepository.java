package com.bankmock.infraestructure.adapter.postgresql.creationtoken.infraestructure;

import com.bankmock.infraestructure.adapter.postgresql.creationtoken.domain.TokenAccountData;
import com.bankmock.infraestructure.adapter.postgresql.creationtoken.domain.TokenData;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends CrudRepository<TokenData, Long> {

    //@Query("SELECT * FROM tokens t WHERE t.token = :token")
    @Query("SELECT t.token, t.id_account, ba.dni_customer, t.commercial_ally " +
            "FROM tokens t JOIN bank_accounts ba ON t.id_account = ba.id " +
            "WHERE t.token = :token AND t.commercial_ally = :commercialAlly")
    TokenAccountData findByTokenCommercialAlly(@Param("token") String token, @Param("commercialAlly") String commercialAlly);

    @Query("SELECT * FROM tokens t WHERE t.token = :token")
    TokenData findByToken(@Param("token") String token);

}
