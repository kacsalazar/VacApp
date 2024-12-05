package com.bankmock.infraestructure.adapter.postgresql.creationtoken.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenAccountData {

    @Column
    private String token;
    @Column(name = "id_account")
    private Long idAccount;
    @Column(name="dni_customer")
    private String dniCustomer;
    @Column(name="commercial_Ally")
    private String commercialAlly;

}
