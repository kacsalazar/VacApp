package com.bankmock.domain.model.createtoken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenAccount {

    private String token;
    private Long idAccount;
    private String dniCustomer;
    private String commercialAlly;
}
