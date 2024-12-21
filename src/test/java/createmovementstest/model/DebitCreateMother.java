package createmovementstest.model;

import com.bankmock.domain.model.createbankingmovement.bankingMovement.DebitCreate;

import java.math.BigDecimal;

public class DebitCreateMother {

    public static DebitCreate build(){
        return DebitCreate.builder()
                .sourceTokenBass("token")
                .amount(new BigDecimal(10000)).build();
    }
}
