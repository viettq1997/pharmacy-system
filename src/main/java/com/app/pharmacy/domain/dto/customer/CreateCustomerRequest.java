package com.app.pharmacy.domain.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(

        @NotBlank(message = "First name is mandatory field")
        String firstName,
        @NotBlank(message = "Last name is mandatory field")
        String lastName,
        Integer age,
        @NotBlank(message = "Sex is mandatory field")
        String sex,
        @NotBlank(message = "Phone no is mandatory field")
        String phoneNo,
        @NotBlank(message = "Mail is mandatory field")
        String mail
) {
}
