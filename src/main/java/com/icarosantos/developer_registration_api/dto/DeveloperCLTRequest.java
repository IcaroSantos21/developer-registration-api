package com.icarosantos.developer_registration_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de requisição para cadastro de desenvolvedor com contrato CLT.
 * Estende {@link DeveloperRequest} com o campo exclusivo do regime CLT.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperCLTRequest extends DeveloperRequest{

    /** Data de admissão do desenvolvedor CLT. Campo Obrigatório*/
    @NotNull
    private LocalDate admissionDate;
}
