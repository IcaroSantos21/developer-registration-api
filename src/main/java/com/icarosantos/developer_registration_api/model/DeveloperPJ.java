package com.icarosantos.developer_registration_api.model;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class DeveloperPJ extends Developer {

    @Column(name = "contract_start_date")
    private LocalDate contractStartDate;

    private int contractPeriod;

}
