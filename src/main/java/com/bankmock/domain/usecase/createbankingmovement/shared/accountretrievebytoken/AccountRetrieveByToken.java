package com.bankmock.domain.usecase.createbankingmovement.shared.accountretrievebytoken;

import com.bankmock.domain.model.createbankingmovement.bankAccount.BankAccount;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.model.createtoken.TokenAccount;
import com.bankmock.domain.model.shared.exception.AppException;
import com.bankmock.domain.model.shared.exception.ConstantException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountRetrieveByToken {
    private final ITokenGateway iTokenGateway;
    private final IBankAccountGateway iBankAccountGateway;

    public BankAccount retrieve(String token, String businessPartner){
        TokenAccount sourceAccount = getAccountByToken(token,
                businessPartner);
        return iBankAccountGateway.findAccountById(sourceAccount.getIdAccount());
    }

    private TokenAccount getAccountByToken(String token, String commercialAlly){
        TokenAccount completeToken = this.iTokenGateway.findByTokenCommercialAlly(token, commercialAlly);
        if (completeToken == null) throw new AppException(ConstantException.INVALID_TOKEN);
        return completeToken;
    }
}
