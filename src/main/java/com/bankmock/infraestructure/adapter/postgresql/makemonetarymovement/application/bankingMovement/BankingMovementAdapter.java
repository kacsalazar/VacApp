package com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.application.bankingMovement;

import com.bankmock.domain.model.createbankingmovement.bankingMovement.BankingMovement;
import com.bankmock.domain.model.createbankingmovement.bankingMovement.IBankingMovementGateway;
import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.application.bankingMovement.mapper.BankingMovementMapper;
import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankingMovement.BankingMovementData;
import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.infraestructure.BankingMovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@AllArgsConstructor
public class BankingMovementAdapter implements IBankingMovementGateway {

    private final BankingMovementRepository bankingMovementRepository;

    @Override
    public BankingMovement createMovement(BankingMovement movement) {
        BankingMovementData movementData = BankingMovementMapper.toData(movement);
        log.info("EN DATA"+movementData);
        return BankingMovementMapper.toEntity(this.bankingMovementRepository.save(movementData));
    }


}