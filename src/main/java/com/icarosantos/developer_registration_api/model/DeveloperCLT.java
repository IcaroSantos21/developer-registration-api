package com.icarosantos.developer_registration_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name= "tb_dev_clt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperCLT extends Developer {

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    private boolean thirteenSalary;

    private boolean paidVacation;
}
