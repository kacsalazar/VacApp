package com.bankmock.domain.model.createtoken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Token {

    private Long id;
    private Long idAccount;
    private String token;
    //Enum
    private String commercialAlly;
    private String status;
}
