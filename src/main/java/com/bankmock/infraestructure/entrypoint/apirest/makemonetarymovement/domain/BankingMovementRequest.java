package com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankingMovementRequest {

    private Meta meta;
    private DataInfo dataInfo;

    @Data
    public static class Meta{

        private String messageId;
        private Date date;
    }

    @Data
    public static class DataInfo {
        private CustomerData customerData;
        private ClientData clientData;
        private MovementInfo movementInfo;
    }

    @Data
    public static class CustomerData {

        private String tokenBaas;
        private CustomerInfo customerInfo;

    }

    @Data
    public static class CustomerInfo{
        private String docType;
        private String docNumber;
    }

    @Data
    public static class ClientData {
        private String bank;
        private CustomerInfo customerInfo;
        private String tokenBaas;
    }

    @Data
    public static class MovementInfo{
        private BigDecimal amount;
    }

}

