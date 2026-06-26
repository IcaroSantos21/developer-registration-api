package com.icarosantos.developer_registration_api.patterns.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        BigDecimal totalBenefits = salary.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP).add(salary);
        return totalBenefits;

    }


}
