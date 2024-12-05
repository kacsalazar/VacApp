package com.bankmock.domain.model.shared.exception;

public class AppException extends RuntimeException{

    private static ConstantException constant;

    public AppException(ConstantException exception){
        super(exception.getError_message());
        constant = exception;

    }

}
