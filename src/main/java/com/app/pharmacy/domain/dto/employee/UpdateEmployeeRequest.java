package com.app.pharmacy.domain.dto.employee;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateEmployeeRequest(
        @NotBlank(message = "Username is mandatory field")
        String username,
        @NotBlank(message = "First name is mandatory field")
        String firstName,
        @NotBlank(message = "Last name is mandatory field")
        String lastName,
        LocalDate birthDate,
        Integer age,
        @NotBlank(message = "Sex is mandatory field")
        String sex,
        @NotBlank(message = "Type is mandatory field")
        String type,
        String address,
        String mail,
        @NotBlank(message = "Phone no is mandatory field")
        String phoneNo,
        BigDecimal salary
) { }
