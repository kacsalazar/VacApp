package com.bankmock.domain.model.createbankingmovement.bankingMovement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BankingMovementEntityResponse {

    private Meta meta;

    @Builder
    @Data
    public static class Meta{
        private String messageId;
        private Date date;
    }
}
