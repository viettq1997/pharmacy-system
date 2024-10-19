package com.app.pharmacy.domain.dto.purchase;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePurchaseRequest(

        @NotBlank(message = "medicineId is mandatory field")
        String medicineId,
        @NotBlank(message = "supplierId is mandatory field")
        String supplierId,
        @NotBlank(message = "locationRackId is mandatory field")
        String locationRackId,
        @NotNull(message = "Quantity is mandatory field")
        Integer quantity,
        @NotNull(message = "Cost is mandatory field")
        BigDecimal cost,
        @NotNull(message = "mfgDate is mandatory field")
        LocalDate mfgDate,
        @NotNull(message = "expDate is mandatory field")
        LocalDate expDate
) {
}
