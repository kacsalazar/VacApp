package com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.infra;

import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.application.BankingMovementHandler;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementRequest;
import com.bankmock.infraestructure.entrypoint.apirest.makemonetarymovement.domain.BankingMovementResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "movement")
@AllArgsConstructor
public class BankingMovementEntryPoint {

    private final BankingMovementHandler bankingMovementHandler;

    @PostMapping()
    public ResponseEntity<BankingMovementResponse> createMovement
            (@RequestBody BankingMovementRequest movementRequest, @RequestHeader String commercialAlly){
        return ResponseEntity.ok(this.bankingMovementHandler.createMovement(movementRequest, commercialAlly));
    }
}
