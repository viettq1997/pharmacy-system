package com.app.pharmacy.domain.dto.medicine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateMedicineRequest(
        @NotBlank(message = "Name is mandatory field")
        String name,
        @NotNull(message = "Price is mandatory field")
        BigDecimal price,
        @NotBlank(message = "Category id is mandatory field")
        String categoryId
) {
}
