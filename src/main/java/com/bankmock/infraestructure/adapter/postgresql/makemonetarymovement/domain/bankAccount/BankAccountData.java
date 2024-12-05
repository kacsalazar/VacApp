package com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankAccount;


import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
//import jakarta.persistence.Table;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "bank_accounts")
public class BankAccountData {

    @Id
    private Long id;
    @Column(name = "no_account")
    private String noAccount;
    @Column
    private BigDecimal amount;
    @Column(name = "dni_customer")
    private String dniCustomer;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "type_account")
    private String typeAccount;

}
