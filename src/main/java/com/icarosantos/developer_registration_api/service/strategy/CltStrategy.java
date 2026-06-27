package com.icarosantos.developer_registration_api.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
