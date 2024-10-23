package com.app.pharmacy.domain.dto.purchase;

import com.app.pharmacy.domain.dto.medicine.MedicineResponse;
import com.app.pharmacy.domain.dto.supplier.SupplierResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPurchaseResponse {

    private String id;

    private MedicineResponse medicine;
    private SupplierResponse supplier;
    private Integer quantity;
    private BigDecimal cost;

    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
}
