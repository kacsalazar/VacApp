package com.bankmock.domain.model.shared.exception;


import lombok.Getter;

@Getter
public class AppException extends RuntimeException{

    private ConstantException constant;

    public AppException(ConstantException exception){
        super(exception.getError_message());
        constant = exception;

    }

}
