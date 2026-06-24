package com.icarosantos.developer_registration_api.patterns.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CltStrategy implements ContractStrategy{
    @Override
    public boolean hasThirteenSalary() {
        return true;
    }

    @Override
    public boolean hasPaidVacation() {
        return true;
    }

    @Override
    public BigDecimal calculateTotalBenefits(BigDecimal salary) {
        BigDecimal totalBenefits = salary.divide(new BigDecimal("3")).add(salary);
        return totalBenefits;

    }


}
