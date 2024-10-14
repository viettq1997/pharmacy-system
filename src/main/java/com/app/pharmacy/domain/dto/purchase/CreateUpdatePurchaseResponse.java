package com.app.pharmacy.domain.dto.purchase;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUpdatePurchaseResponse {

    private String id;

    private String medicineId;
    private String supplierId;
    private Integer quantity;
    private BigDecimal cost;
    private LocalDate purchaseDate;
    private LocalDate mfgDate;
    private LocalDate expDate;

    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
}