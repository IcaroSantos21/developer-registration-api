package com.icarosantos.developer_registration_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperCLTRequest extends DeveloperRequest{

    @NotNull
    private LocalDate admissionDate;
}
