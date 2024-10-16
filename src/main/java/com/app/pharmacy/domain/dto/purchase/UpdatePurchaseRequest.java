package com.app.pharmacy.domain.dto.purchase;

import java.math.BigDecimal;

public record UpdatePurchaseRequest(
        String medicineId,
        String supplierId,
        Integer quantity,
        BigDecimal cost
) {
}
