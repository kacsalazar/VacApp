package com.bankmock.domain.usecase.createbankingmovement.createdebit;

import com.bankmock.domain.model.createbankingmovement.IExternalBankConsumer;
import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.IBankingMovementGateway;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.usecase.createbankingmovement.CreditMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.bankmock.domain.usecase.createbankingmovement.createdebit.mapper.DebitCreatorMapper.buildMovementModel;


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
    void testInvalidToken() {
        //given
        DebitCreate debitCreate = DebitCreate.builder()
                .sourceTokenBass("asd1234")
                .sourceDocNumber("234")
                .targetBank("BANCO_A")
                .targetDocNumber("456")
                .targetTokenBass("qwe7894")
                .amount(new BigDecimal(200000))
                .build();

        String commercialAlly = "VAQAPP";
        BankAccount account = BankAccount.builder()
                .id(1L)
                .noAccount("1234")
                .amount(new BigDecimal(8000000))
                .dniCustomer("234")
                .isActive(Boolean.TRUE)
                .typeAccount("Ahorros")
                .build();

        BankingMovement movement = BankingMovement.builder()
                .id(1L)
                .typeMovement("Debit")
                .customerAccountId(1L)
                .amount(new BigDecimal(200000))
                .token("asd1234")
                .bank("BANCO_A")
                .status("SUCCESSFUL")
                .build();

        //when
        when(debitValidator.validateMovement(debitCreate, commercialAlly))
                .thenReturn(1L);
        when(iBankAccountGateway.findAccountById(1L))
                .thenReturn(account);
        when(iBankingMovementGateway.createMovement(movement))
                .thenReturn(123L);

        when(iExternalBankConsumer.notifyBank())
                .thenReturn(Boolean.FALSE);

        debitCreator.createMovement(debitCreate, commercialAlly);

        //then
        assertThrows(AppException.class, () -> debitCreator
                .createMovement(debitCreate, commercialAlly));

    }

}