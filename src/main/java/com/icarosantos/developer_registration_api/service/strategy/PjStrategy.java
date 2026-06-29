package com.icarosantos.developer_registration_api.service.strategy;

import com.icarosantos.developer_registration_api.model.TypeContract;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * Implementação do {@link ContractStrategy} para desenvolvedores com contrato PJ.
 * Define as regras de benefícios para o regime PJ: férias remuneradas e sem 13º salário.
 */
@Component
public class PjStrategy implements ContractStrategy{

    /**
     * @return false — PJ não tem direito ao 13º salário
     */
    @Override
    public boolean hasThirteenSalary() {
        return false;
    }

    /**
     * @return true — PJ tem direito a férias remuneradas
     */
    @Override
    public boolean hasPaidVacation() {
        return true;
    }

    /**
     * Calcula a data de fim do contrato somando o período em meses à data de início.
     *
     * @param startDate data de início do contrato
     * @param periodIntMonths duração do contrato em meses
     * @return data de fim do contrato
     */
    @Override
    public LocalDate calculateContractEndDate(LocalDate startDate, Integer periodIntMonths) {
        var endDate = startDate.plusMonths(periodIntMonths);
        return endDate;
    }

    /**
     * Calcula o total de benefícios para PJ: apenas férias remuneradas (salário / 3).
     *
     * @param salary salário do desenvolvedor
     * @return valor total dos benefícios
     */
    @Override
    public BigDecimal calculateTotalBenefits(BigDecimal salary) {
        BigDecimal totalBenefits = salary.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
        return totalBenefits;
    }

    @Override
    public TypeContract getType() {
        return TypeContract.PJ;
    }
}
