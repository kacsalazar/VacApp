package com.bankmock.domain.model.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConstantException {

    INVALID_TOKEN("Token is invalid", "404-01"),
    INVALID_ACCOUNT("The account is not active", "404-01"),
    INVALID_COMMERCIAL_ALLY("The trading partner does not have a valid token", "404-01"),
    PAYMENT_FAILED("Failed payment to another bank", "404-01"),
    INSUFFICIENT_AMOUNT("Insufficient amount to complete the transaction", "404-01"),
    NONEXISTENT_ACCOUNT("There is no bank account with this number", "404-01");

    private final String error_message;
    private final String code_error;


}
