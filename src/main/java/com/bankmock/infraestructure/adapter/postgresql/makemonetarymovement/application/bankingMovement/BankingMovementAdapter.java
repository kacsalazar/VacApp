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
    public Long createMovement(BankingMovement movement) {
        BankingMovementData movementData = BankingMovementMapper.toData(movement);
        return BankingMovementMapper.toEntity(this.bankingMovementRepository.save(movementData)).getId();
    }

    @Override
    public BankingMovement findMovementById(Long id) {
        return BankingMovementMapper.toEntity(this.bankingMovementRepository.findById(id).get());
    }

    @Override
    public void updateBankingMovement(BankingMovement movement) {
        this.bankingMovementRepository.save(BankingMovementMapper.toData(movement));
    }


}
