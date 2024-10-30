package com.app.pharmacy.domain.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefundResponse {

    private String medicineName;
    private Integer quantity;
    private BigDecimal refundAmount;
    private String code;
    private SaleType type;
}