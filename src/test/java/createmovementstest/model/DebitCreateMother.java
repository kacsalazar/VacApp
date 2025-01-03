package createmovementstest.model;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;

import java.math.BigDecimal;

public class DebitCreateMother {

    public static DebitCreate build(){
        return DebitCreate.builder()
                .sourceTokenBass("token")
                .targetBank("BANCO_A")
                .amount(new BigDecimal(100)).build();
    }

    public static DebitCreate buildToExternalCustomer(){
        return DebitCreate.builder()
                .sourceTokenBass("token")
                .targetBank("BANCO_B")
                .amount(new BigDecimal(10000)).build();
    }

    public static BankAccount buildAccountNotActivated(){
        return BankAccount.builder()
                .isActive(Boolean.FALSE)
                .amount(new BigDecimal(10000)).build();
    }

    public static BankAccount buildAccountWithOutSufficientAmount(){
        return BankAccount.builder()
                .isActive(Boolean.TRUE)
                .amount(new BigDecimal(100)).build();
    }

    public static BankAccount buildAccount(){
        return BankAccount.builder()
                .isActive(Boolean.TRUE)
                .amount(new BigDecimal(300000)).build();
    }

    public  static BankingMovement buildMovement(){
        return  BankingMovement.builder()
                .amount(new BigDecimal(10000))
                .build();
    }
}
