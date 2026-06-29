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

/**
 * Classe base abstrata para os DTOs de requisição de cadastro de desenvolvedor
 * Contém os campos comuns a todos os tipos de contrato.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class DeveloperRequest {

    /** Nome do Desenvolvedor. Campo Obrigatório */
    @NotBlank
    private String firstName;

    /** Sobrenome do Desenvolvedor. Campo Obrigatório */
    @NotBlank
    private String lastName;

    /** Data de nascimento do Desenvolvedor. Campo Obrigatório */
    @NotNull
    private LocalDate birthDate;

    /** Nome da empresa do Desenvolvedor. Campo Obrigatório */
    @NotBlank
    private String enterprise;

    /** Salário do Desenvolvedor. Campo Obrigatório */
    @NotNull
    private BigDecimal salary;

    /** Tipo do Desenvolvedor: FRONT ou BACK. Campo Obrigatório */
    @NotNull
    private TypeDeveloper typeDeveloper;

    /** Data das Férias do Desenvolvedor. Campo Opcional */
    private LocalDate vacationDate;

    /** CEP para a busca do endereço via ViaCEP. Campo Obrigatório */
    @NotBlank
    private String cep;

}
