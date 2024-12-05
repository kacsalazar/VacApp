package com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.infraestructure;

import com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.bankAccount.BankAccountData;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BankAccountRepository extends CrudRepository<BankAccountData, Long> {

    @Query("SELECT * FROM bank_accounts bm WHERE bm.no_account = :noAccount")
    BankAccountData findByNoAccount(@Param("noAccount") String noAccount);
}
