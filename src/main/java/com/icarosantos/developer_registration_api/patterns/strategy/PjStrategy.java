package com.icarosantos.developer_registration_api.patterns.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PjStrategy implements ContractStrategy{
    @Override
    public boolean hasThirteenSalary() {
        return false;
    }

    @Override
    public boolean hasPaidVacation() {
        return true;
    }

    @Override
    public LocalDate calculateContractEndDate(LocalDate startDate, Integer periodIntMonths) {
        var endDate = startDate.plusMonths(periodIntMonths);
        return endDate;
    }

    @Override
    public BigDecimal calculateTotalBenefits(BigDecimal salary) {
        BigDecimal totalBenefits = salary.divide(new BigDecimal("3"));
        return totalBenefits;
    }
}
