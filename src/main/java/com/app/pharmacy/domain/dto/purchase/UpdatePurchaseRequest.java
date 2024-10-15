package com.app.pharmacy.domain.dto.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdatePurchaseRequest(
        String medicineId,
        String supplierId,
        Integer quantity,
        BigDecimal cost,
        LocalDate purchaseDate,
        LocalDate mfgDate,
        LocalDate expDate
) {
}
