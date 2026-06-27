package com.icarosantos.developer_registration_api.service.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ContractStrategy {

    boolean hasThirteenSalary();
    boolean hasPaidVacation();
    default LocalDate calculateContractEndDate(LocalDate startDate, Integer periodIntMonths) {
        return null;
    };
    BigDecimal calculateTotalBenefits(BigDecimal salary);
}
