package com.bankmock.domain.model.createbankingmovement.bankAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountByToken {

    private String account;
    private String dniClient;
    private Long idAccount;
}
