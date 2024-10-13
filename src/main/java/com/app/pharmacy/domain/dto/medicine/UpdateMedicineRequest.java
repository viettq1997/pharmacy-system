package com.app.pharmacy.domain.dto.medicine;

import java.math.BigDecimal;

public record UpdateMedicineRequest(
        String name,
        Integer quantity,
        BigDecimal price,
        String category,
        String locationRack
) {
}
