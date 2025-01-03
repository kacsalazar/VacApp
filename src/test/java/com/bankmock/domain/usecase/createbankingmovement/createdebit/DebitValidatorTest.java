package com.bankmock.domain.usecase.createbankingmovement.createdebit;


import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.model.shared.exception.ConstantException;
import com.bankmock.domain.usecase.createbankingmovement.shared.accountretrievebytoken.AccountRetrieveByToken;
import createmovementstest.model.DebitCreateMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DebitValidatorTest {

    @Mock
    AccountRetrieveByToken accountRetrieveByToken;

    @InjectMocks
    DebitValidator debitValidator;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBankAccountNonExits()
    {
        //given
        DebitCreate debitCreate = DebitCreateMother.build();
        String businessPartner = "VAQAPP";

        //when
        when(accountRetrieveByToken.retrieve(debitCreate.getSourceTokenBass(),
                businessPartner)).thenReturn(null);

        //then
        AppException ex = assertThrows(AppException.class, () -> debitValidator.validateMovementAndGetAccount(
                debitCreate, businessPartner));
        assertEquals(ex.getConstant().getCode_error(),
                ConstantException.NONEXISTENT_ACCOUNT.getCode_error());
    }

    @Test
    void testBankAccountNotActivated(){
        //given
        DebitCreate debitCreate = DebitCreateMother.build();
        String businessPartner = "VAQAPP";
        BankAccount account = DebitCreateMother.buildAccountNotActivated();

        //when
        when(accountRetrieveByToken.retrieve(debitCreate.getSourceTokenBass(),
                businessPartner)).thenReturn(account);

        //then
        AppException ex = assertThrows(AppException.class, () -> debitValidator.validateMovementAndGetAccount(
                debitCreate, businessPartner));
        assertEquals(ex.getConstant().getCode_error(),
                ConstantException.INVALID_ACCOUNT.getCode_error());
    }

    @Test
    void testBankAccountWithOutSufficientAmount(){
        //given
        DebitCreate debitCreate = DebitCreateMother.build();
        String businessPartner = "VAQAPP";
        BankAccount account = DebitCreateMother.buildAccountWithOutSufficientAmount();

        //when
        when(accountRetrieveByToken.retrieve(debitCreate.getSourceTokenBass(),
                businessPartner)).thenReturn(account);

        //then
        AppException ex = assertThrows(AppException.class, () -> debitValidator.validateMovementAndGetAccount(
                debitCreate, businessPartner));
        assertEquals(ex.getConstant().getCode_error(),
                ConstantException.INSUFFICIENT_AMOUNT.getCode_error());
    }

    @Test
    void testValidateAccount() {

        //given
        DebitCreate debitCreate = DebitCreateMother.build();
        String businessPartner = "VAQAPP";
        BankAccount account = DebitCreateMother.buildAccount();

        //when
        when(accountRetrieveByToken.retrieve(debitCreate.getSourceTokenBass(),
                businessPartner)).thenReturn(account);

        //then
        BankAccount bankAccount = debitValidator.validateMovementAndGetAccount(debitCreate, businessPartner);

        assertEquals(bankAccount, account);
    }
}