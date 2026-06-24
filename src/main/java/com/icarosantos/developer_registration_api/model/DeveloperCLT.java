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
@Table(name= "tb_dev_clt")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DeveloperCLT extends Developer {

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    private boolean thirteenSalary;
}
