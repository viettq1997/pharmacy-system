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

import java.time.LocalDateTime;

@Entity
@Table(name = "SUPPLIERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supplier {

    @Id
    @Column(name = "Sup_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "Sup_Name")
    private String name;
    @Column(name = "Sup_Add")
    private String address;
    @Column(name = "Sup_Phno")
    private String phoneNo;
    @Column(name = "Sup_Mail")
    private String mail;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "updated_by")
    private String updatedBy;
}
