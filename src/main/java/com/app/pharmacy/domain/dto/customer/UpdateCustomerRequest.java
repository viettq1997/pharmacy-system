package com.app.pharmacy.domain.dto.customer;

public record UpdateCustomerRequest(

        String firstName,
        String lastName,
        Integer age,
        String sex,
        String phoneNo,
        String mail
) {
}
