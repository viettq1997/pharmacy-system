package com.app.pharmacy.domain.dto.medicine;

import java.math.BigDecimal;

public record UpdateMedicineRequest(
        String name,
        BigDecimal price,
        String categoryId
) {
}
