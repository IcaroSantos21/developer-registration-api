package com.icarosantos.developer_registration_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperPJRequest extends DeveloperRequest{
    private LocalDate contractStartDate;
    private Integer contractPeriod;
}
