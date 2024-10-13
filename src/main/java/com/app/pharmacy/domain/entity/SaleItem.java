package com.app.pharmacy.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SALES_ITEMS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleItem {

    @EmbeddedId
    private MedSaleId id;

    @Column(name = "Sale_Qty")
    private Integer quantity;
    @Column(name = "Tot_Price")
    private BigDecimal totalPrice;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "updated_by")
    private String updatedBy;
}
