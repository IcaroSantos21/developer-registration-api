package com.icarosantos.developer_registration_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icarosantos.developer_registration_api.model.TypeContract;
import com.icarosantos.developer_registration_api.model.TypeDeveloper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String enterprise;

    @NotNull
    private BigDecimal salary;

    @NotNull
    private TypeDeveloper typeDeveloper;

    private LocalDate vacationDate;

    @NotBlank
    private String cep;

}
