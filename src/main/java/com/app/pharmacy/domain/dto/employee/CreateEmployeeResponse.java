package com.app.pharmacy.domain.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmployeeResponse {
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Integer age;
    private String sex;
    private String type;
    private LocalDateTime joinDate;
    private String address;
    private String mail;
    private String phoneNo;
    private BigDecimal salary;
}
