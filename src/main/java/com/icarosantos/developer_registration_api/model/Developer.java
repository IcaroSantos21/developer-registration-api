package com.icarosantos.developer_registration_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String enterprise;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private TypeDeveloper typeDeveloper;

    @Enumerated(EnumType.STRING)
    private TypeContract typeContract;

    @Column(name = "holiday_date")
    private LocalDate holidayDate;

    @Embedded
    private Address address;

}
