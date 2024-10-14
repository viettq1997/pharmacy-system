package com.app.pharmacy.domain.dto.purchase;

import java.time.LocalDate;

public record GetPurchaseRequest(
        String medicineId, String supplierId, LocalDate purDateBegin, LocalDate purDateEnd, Boolean outOfDate) {
}
