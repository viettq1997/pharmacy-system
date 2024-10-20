package com.app.pharmacy.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SALE_LOG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleLog {

    @Id
    @Column(name = "Sale_ID")
    private String saleId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "Total_Amt")
    private BigDecimal totalAmount;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne
    @JoinColumn(name = "Sale_ID", referencedColumnName = "Sale_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), updatable = false, insertable = false)
    private Sale sale;
}
