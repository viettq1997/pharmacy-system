package com.app.pharmacy.domain.dto.medicine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateMedicineRequest(
        @NotBlank(message = "Name is mandatory field")
        String name,
        @NotNull(message = "Quantity is mandatory field")
        Integer quantity,
        @NotNull(message = "Price is mandatory field")
        BigDecimal price,
        @NotBlank(message = "Category is mandatory field")
        String category,
        @NotBlank(message = "Location rack is mandatory field")
        String locationRack
) {
}
