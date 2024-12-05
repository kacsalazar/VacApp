package com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.infraestructure;

import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankingMovement.BankingMovementData;
import org.springframework.data.repository.CrudRepository;

public interface BankingMovementRepository extends CrudRepository<BankingMovementData, Long> {
}
