package com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankingMovement;

import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
//import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "banking_movements")
public class BankingMovementData {

    @Id
    private Long id;
    @Column(name = "type_movement")
    private String typeMovement;
    @Column(name = "customer_account_id")
    private Long customerAccountId;
    @Column
    private BigDecimal amount;
    @Column
    private String token;
    @Column
    private String bank;
    @Column
    private String status;
}
