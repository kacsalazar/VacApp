package com.bankmock.domain.model.createbankingmovement.bankingMovement;


import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DebitCreate {

    private String sourceTokenBass;
    private String sourceDocNumber;

    private String targetBank;
    private String targetDocNumber;
    private String targetTokenBass;

    private BigDecimal amount;
}
