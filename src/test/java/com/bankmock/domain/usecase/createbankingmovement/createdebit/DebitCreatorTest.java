package com.bankmock.domain.usecase.createbankingmovement.createdebit;

import com.bankmock.domain.model.createbankingmovement.IExternalBankConsumer;
import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.IBankingMovementGateway;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.model.shared.exception.ConstantException;
import com.bankmock.domain.usecase.createbankingmovement.creditcreate.CreditCreator;
import createmovementstest.model.DebitCreateMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static com.bankmock.domain.usecase.createbankingmovement.createdebit.mapper.DebitCreatorMapper.buildMovementModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class DebitCreatorTest {

    @Mock
    IExternalBankConsumer iExternalBankConsumer;
    @Mock
    CreditCreator creditCreator;
    @Mock
    IBankingMovementGateway iBankingMovementGateway;
    @Mock
    DebitValidator debitValidator;
    @Mock
    IBankAccountGateway iBankAccountGateway;
    @InjectMocks
    DebitCreator debitCreator;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPaymentToOtherCustomerFailed() {
        //given
        String businessPartner = "VAQAPP";
        DebitCreate debitCreate =  DebitCreateMother.buildToExternalCustomer();
        BankAccount account = DebitCreateMother.buildAccount();
        BankingMovement movement = DebitCreateMother.buildMovement();

        //when
        when(iExternalBankConsumer.notifyBank()).thenReturn(Boolean.FALSE);
        when(debitValidator.validateMovementAndGetAccount
                (debitCreate, businessPartner)).thenReturn(account);
        when(iBankingMovementGateway.createMovement
                (buildMovementModel(debitCreate, account, "Debit"))).thenReturn(movement);

        //then
        AppException ex = assertThrows(AppException.class, () -> debitCreator.debit(debitCreate, businessPartner));
        assertEquals(ex.getConstant().getCode_error(),
                ConstantException.PAYMENT_FAILED.getCode_error());
    }

    @Test
    void testPaymentToExternalCustomerSuccessful(){
        //given
        String businessPartner = "VAQAPP";
        DebitCreate debitCreate =  DebitCreateMother.buildToExternalCustomer();
        BankAccount account = DebitCreateMother.buildAccount();
        BankingMovement movement = DebitCreateMother.buildMovement();

        //when
        when(iExternalBankConsumer.notifyBank()).thenReturn(Boolean.TRUE);
        when(debitValidator.validateMovementAndGetAccount
                (debitCreate, businessPartner)).thenReturn(account);
        when(iBankingMovementGateway.createMovement
                (buildMovementModel(debitCreate, account, "Debit"))).thenReturn(movement);

        //then
        debitCreator.debit(debitCreate, businessPartner);

        verify(iBankingMovementGateway, times(1)).updateBankingMovement(movement);
    }

    @Test
    void testPaymentToCustomerSuccessful(){
        //given
        String businessPartner = "VAQAPP";
        DebitCreate debitCreate =  DebitCreateMother.build();
        BankAccount account = DebitCreateMother.buildAccount();
        BankingMovement movement = DebitCreateMother.buildMovement();

        //when
        when(debitValidator.validateMovementAndGetAccount
                (debitCreate, businessPartner)).thenReturn(account);
        when(iBankingMovementGateway.createMovement
                (buildMovementModel(debitCreate, account, "Debit"))).thenReturn(movement);
        when(creditCreator.byToken("token", new BigDecimal(100))).thenReturn(Boolean.TRUE);

        //then
        debitCreator.debit(debitCreate, businessPartner);

        verify(iBankingMovementGateway, times(1)).updateBankingMovement(movement);
    }

}