package com.app.pharmacy.domain.dto.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdatePurchaseRequest(
        String medicineId,
        LocalDate mfgDate,
        String supplierId,
        Integer quantity,
        BigDecimal cost,
        String locationRackId,
        LocalDate expDate
) {
}
