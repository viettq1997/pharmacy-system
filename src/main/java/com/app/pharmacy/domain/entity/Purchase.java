package com.app.pharmacy.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PURCHASE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {

    @Id
    @Column(name = "P_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "Med_ID")
    private String medicineId;
    @Column(name = "Sup_ID")
    private String supplierId;
    @Column(name = "P_Qty")
    private Integer quantity;
    @Column(name = "P_Cost")
    private BigDecimal cost;
    @Column(name = "Pur_Date")
    private LocalDate purchaseDate;
    @Column(name = "Mfg_Date")
    private LocalDate mfgDate;
    @Column(name = "Exp_Date")
    private LocalDate expDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "updated_by")
    private String updatedBy;
}
