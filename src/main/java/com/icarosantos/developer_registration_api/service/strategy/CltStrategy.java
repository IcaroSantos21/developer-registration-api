package com.icarosantos.developer_registration_api.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementação do {@link ContractStrategy} para desenvolvedores com contrato CLT.
 * Define as regras de benefícios para o regime CLT: férias remuneradas e 13º salário.
 */
public class CltStrategy implements ContractStrategy{

    /**
     * @return true — CLT tem direito ao 13º salário
     */
    @Override
    public boolean hasThirteenSalary() {
        return true;
    }

    /**
     * @return true — CLT tem direito a férias remuneradas
     */
    @Override
    public boolean hasPaidVacation() {
        return true;
    }

    /**
     * Calcula o total de benefícios para CLT: férias (salário / 3) + 13º (salário).
     *
     * @param salary salário do desenvolvedor
     * @return valor total dos benefícios
     */
    @Override
    public BigDecimal calculateTotalBenefits(BigDecimal salary) {
        BigDecimal totalBenefits = salary.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP).add(salary);
        return totalBenefits;

    }


}
