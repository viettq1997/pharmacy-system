package com.app.pharmacy.domain.dto.medicine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateMedicineRequest(
        @NotBlank(message = "name is mandatory field")
        String name,
        @NotNull(message = "price is mandatory field")
        BigDecimal price,
        @NotBlank(message = "categoryId is mandatory field")
        String categoryId
) {
}
