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
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import static com.bankmock.domain.usecase.createbankingmovement.createdebit.mapper.DebitCreatorMapper.buildMovementModel;

@Service
@Log4j2
@AllArgsConstructor
public class DebitCreator {

    private final IBankingMovementGateway iBankingMovementGateway;
    private final DebitValidator debitValidator;
    private final IBankAccountGateway iBankAccountGateway;
    private final CreditCreator creditCreator;
    private final IExternalBankConsumer creditToExternalUser;

    public void debit(DebitCreate movement, String commercialAlly){

        BankAccount accountToDebit = debitValidator.validateMovementAndGetAccount(movement, commercialAlly);
        // TODO: 17/12/24 Recuperar cuenta de cliente acá, quitarlo de debir validator
        debitAccount(movement.getAmount(), accountToDebit);

        BankingMovement registeredMovement = iBankingMovementGateway.createMovement(
                buildMovementModel(movement, accountToDebit, "Debit"));

        Boolean isSuccessful = creditToTargetUser(movement);

        if (isSuccessful.equals(Boolean.FALSE)) {
            reverseDebit(accountToDebit, registeredMovement);
        }
        registeredMovement.setStatus("SUCCESSFUL");
        iBankingMovementGateway.updateBankingMovement(registeredMovement);
    }

    private void debitAccount(BigDecimal amount, BankAccount account){
        account.setAmount(account.getAmount().subtract(amount));
        this.iBankAccountGateway.saveAmountAccount(account);
    }

    private Boolean creditToTargetUser(DebitCreate bmEntityRequest){
        String bank = "BANCO_A";
        Boolean isOurBank = bank.equals(bmEntityRequest.getTargetBank());

        if(Boolean.TRUE.equals(isOurBank)){
            return creditCreator.byToken(bmEntityRequest.getTargetTokenBass(),
                    bmEntityRequest.getAmount());
        }else {
            return creditToExternalUser.notifyBank();
        }
    }

    private void reverseDebit(BankAccount bankAccountToReverse, BankingMovement movementToSetFailedStatus){
        movementToSetFailedStatus.setStatus("FAILED");
        iBankingMovementGateway.updateBankingMovement(movementToSetFailedStatus);

        creditCreator.byAccount(bankAccountToReverse, movementToSetFailedStatus.getAmount());

        throw new AppException(ConstantException.PAYMENT_FAILED);
    }

}
