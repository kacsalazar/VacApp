package com.bankmock.infraestructure.adapter.postgresql.creationtoken.domain;

import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
//import jakarta.persistence.Table;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tokens")
public class TokenData {

    @Id
    private Long id;
    @Column(name = "id_account")
    private Long idAccount;
    @Column
    private String token;
    //Enum
    @Column(name = "commercial_ally")
    private String commercialAlly;
    @Column
    private String status;
}
