package com.icarosantos.developer_registration_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de requisição para cadastro de desenvolvedor com contrato PJ.
 * Estende {@link DeveloperRequest} com os campos exclusivos do regime PJ.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperPJRequest extends DeveloperRequest{

    /** Data de início do contrato PJ. Campo Obrigatório*/
    @NotNull
    private LocalDate contractStartDate;

    /** Duração de contrato em meses. Campo Obrigatório*/
    @NotNull
    private Integer contractPeriod;
}
