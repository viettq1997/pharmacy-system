package com.app.pharmacy.domain.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(

        @NotBlank(message = "firstName is mandatory field")
        String firstName,
        String lastName,
        Integer age,
        @NotBlank(message = "sex is mandatory field")
        String sex,
        @NotBlank(message = "phoneNo is mandatory field")
        String phoneNo,
        String mail
) {
}
