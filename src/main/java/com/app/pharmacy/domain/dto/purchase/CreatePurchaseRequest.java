package com.app.pharmacy.domain.dto.purchase;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreatePurchaseRequest(

        @NotBlank(message = "Medicine id is mandatory field")
        String medicineId,
        @NotBlank(message = "Supplier id is mandatory field")
        String supplierId,
        @NotNull(message = "Quantity id is mandatory field")
        Integer quantity,
        @NotNull(message = "Cost id is mandatory field")
        BigDecimal cost
) {
}