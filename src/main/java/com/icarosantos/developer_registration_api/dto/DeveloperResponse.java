package com.icarosantos.developer_registration_api.dto;

import com.icarosantos.developer_registration_api.model.Address;
import com.icarosantos.developer_registration_api.model.TypeDeveloper;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperResponse {
    private long id;
    private String fullName;
    private String enterprise;
    private BigDecimal salary;
    private TypeDeveloper typeDeveloper;
    private Address address;
    private LocalDate vacationDate;
    private BigDecimal totalBenefits;
    private LocalDate contractEndDate;
}
