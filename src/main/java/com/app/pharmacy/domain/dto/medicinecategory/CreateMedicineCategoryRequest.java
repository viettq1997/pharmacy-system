package com.app.pharmacy.domain.dto.medicinecategory;

import jakarta.validation.constraints.NotBlank;

public record CreateMedicineCategoryRequest(
        @NotBlank(message = "Name is mandatory field")
        String name,
        @NotBlank(message = "Description is mandatory field")
        String description
) {
}
