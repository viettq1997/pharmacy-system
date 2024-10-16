package com.app.pharmacy.domain.dto.purchase;

import java.time.LocalDate;

public record GetPurchaseRequest(
        LocalDate purDateBegin, LocalDate purDateEnd) {
}
