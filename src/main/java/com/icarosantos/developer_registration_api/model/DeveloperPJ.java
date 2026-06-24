package com.icarosantos.developer_registration_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "tb_dev_pj")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DeveloperPJ extends Developer {

    @Column(name = "contract_start_date")
    private LocalDate contractStartDate;

    private int contractPeriod;

}
