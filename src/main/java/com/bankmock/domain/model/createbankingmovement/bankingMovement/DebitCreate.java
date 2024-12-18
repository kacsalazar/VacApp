package com.bankmock.domain.model.createbankingmovement.bankingMovement;


import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DebitCreate {

    private Meta meta;
    private DataInfo dataInfo;


    @Data
    @Builder
    public static class Meta{

        private String messageId;
        private Date date;
    }

    @Data
    @Builder
    public static class DataInfo {
        private CustomerData customerData;
        private ClientData clientData;
        private MovementInfo movementInfo;
    }

    @Data
    @Builder
    public static class CustomerData {

        private String tokenBaas;
        private CustomerInfo customerInfo;

    }

    @Data
    @Builder
    public static class CustomerInfo{
        private String docType;
        private String docNumber;
    }

    @Data
    @Builder
    public static class ClientData {
        private String bank;
        private CustomerInfo customerInfo;
        private String tokenBaas;
    }

    @Data
    @Builder
    public static class MovementInfo{
        private BigDecimal amount;
        private String typeMovement;
    }
}
