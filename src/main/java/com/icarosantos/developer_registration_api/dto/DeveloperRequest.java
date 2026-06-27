package com.icarosantos.developer_registration_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icarosantos.developer_registration_api.model.TypeContract;
import com.icarosantos.developer_registration_api.model.TypeDeveloper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class DeveloperRequest {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String enterprise;
    private BigDecimal salary;
    private TypeDeveloper typeDeveloper;
    private LocalDate vacationDate;
    private String cep;

}
