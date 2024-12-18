package com.bankmock.domain.usecase.createbankingmovement.createdebit;

import com.bankmock.domain.model.createbankingmovement.IExternalBankConsumer;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.IBankingMovementGateway;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.usecase.createbankingmovement.CreditMovement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DebitCreatorTest {

    @Mock
    IExternalBankConsumer iExternalBankConsumer;
    @Mock
    CreditMovement creditMovement;
    @Mock
    IBankingMovementGateway iBankingMovementGateway;
    @Mock
    DebitValidator debitValidator;
    @Mock
    IBankAccountGateway iBankAccountGateway;
    @Mock
    ITokenGateway iTokenGateway;
    @InjectMocks
    DebitCreator debitCreator;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInvalidToken(){
        //given
        DebitCreate debitCreate = null;
        String commercialAlly = "VAQAPP";

        //when
        when(iBankingMovementGateway.createMovement(any()))
                .thenReturn(123L);


        when(iExternalBankConsumer.notifyBank())
                .thenReturn(Boolean.FALSE);


        //then
        assertThrows(AppException.class, () -> debitCreator
                .createMovement(debitCreate, commercialAlly));

    }

}