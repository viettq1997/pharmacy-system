package com.app.pharmacy.domain.dto.supplier;

import jakarta.validation.constraints.NotBlank;

public record CreateSupplierRequest(
        @NotBlank(message = "Name is mandatory field")
        String name,
        @NotBlank(message = "Address is mandatory field")
        String address,
        @NotBlank(message = "Phone no is mandatory field")
        String phoneNo,
        String mail
) {
}
