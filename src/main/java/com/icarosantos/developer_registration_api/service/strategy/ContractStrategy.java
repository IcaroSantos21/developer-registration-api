package com.icarosantos.developer_registration_api.service.strategy;

import com.icarosantos.developer_registration_api.model.TypeContract;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Interface que define o contrato para as regras de negócio por tipo de contrato.
 * Implementada pelo padrão Strategy para isolar os comportamentos de CLT e PJ.
 */
public interface ContractStrategy {

    /**
     * Verifica se o tipo de contrato tem direito ao 13º salário.
     *
     * @return true se tiver direito, false caso contrário
     */
    boolean hasThirteenSalary();

    /**
     * Verifica se o tipo de contrato tem direito a férias remuneradas.
     *
     * @return true se tiver direito, false caso contrário
     */
    boolean hasPaidVacation();

    /**
     * Calcula a data de fim do contrato com base na data de início e no período em meses.
     * Por padrão retorna null — sobrescrito apenas por contratos com prazo definido (PJ).
     *
     * @param startDate data de início do contrato
     * @param periodIntMonths duração do contrato em meses
     * @return data de fim do contrato ou null para contratos sem prazo definido
     */
    default LocalDate calculateContractEndDate(LocalDate startDate, Integer periodIntMonths) {
        return null;
    };

    /**
     * Calcula o valor total dos benefícios com base no salário.
     *
     * @param salary salário do desenvolvedor
     * @return valor total dos benefícios calculados
     */
    BigDecimal calculateTotalBenefits(BigDecimal salary);

    /**
     * Retorna o tipo de contrato suportado por esta Strategy.
     * Utilizado pelo {@link ContractStrategyResolver} para identificar a Strategy correta.
     *
     * @return {@link TypeContract} suportado por esta implementação
     */
    TypeContract getType();
}
