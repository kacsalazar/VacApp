package com.bankmock.infraestructure.adapter.restconsumer;

import com.bankmock.domain.model.createbankingmovement.IExternalBankConsumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ExternalBankConsumer implements IExternalBankConsumer {

    @Override
    public Boolean notifyBank() {
        return false;
    }
}
