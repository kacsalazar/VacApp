package com.bankmock.infraestructure.adapter.postgresql.makemonetarymovement.domain.customer;

import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
//import jakarta.persistence.Id;
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
@Table(name = "customers")
public class CustomerData {

    @Id
    private Long id;
    @Column
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "dni_customer")
    private String dni_customer;
}
