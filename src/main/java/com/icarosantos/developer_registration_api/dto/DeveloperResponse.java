package com.icarosantos.developer_registration_api.dto;

import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.TypeDeveloper;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de resposta para os dados de um desenvolvedor cadastrado.
 * Contém os dados cadastrais e os benefícios calculados via Strategy.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperResponse {

    /** Identificador único do desenvolvedor */
    private long id;

    /** Nome completo do desenvolvedor */
    private String fullName;

    /** Nome da empresa */
    private String enterprise;

    /** Salário do desenvolvedor */
    private BigDecimal salary;

    /** Tipo do desenvolvedor: BACK ou FRONT */
    private TypeDeveloper typeDeveloper;

    /** Endereço completo do desenvolvedor*/
    private Address address;

    /** Data das férias do desenvolvedor */
    private LocalDate vacationDate;

    /** Valor total dos benefícios calculados via Strategy */
    private BigDecimal totalBenefits;

    /** Data de fim do contrato. Preenchido apenas para desenvolvedor PJ.*/
    private LocalDate contractEndDate;
}
