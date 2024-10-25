package com.app.pharmacy.domain.dto.sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SaleItemRequest(
        @NotBlank(message = "inventoryId is mandatory field")
        String inventoryId,
        @NotNull(message = "quantity is mandatory field")
        Integer quantity,
        @NotNull(message = "price is mandatory field")
        BigDecimal price
) {
}
