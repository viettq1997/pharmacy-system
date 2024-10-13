package com.app.pharmacy.domain.dto.employee;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateEmployeeRequest(
        String username,
        String firstName,
        String lastName,
        LocalDate birthDate,
        Integer age,
        String sex,
        String type,
        String address,
        String mail,
        String phoneNo,
        BigDecimal salary
) { }
