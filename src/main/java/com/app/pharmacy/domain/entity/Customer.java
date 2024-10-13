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
@Table(name = "CUSTOMER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @Column(name = "C_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "C_Fname")
    private String firstName;
    @Column(name = "C_Lname")
    private String lastName;
    @Column(name = "C_Age")
    private Integer age;
    @Column(name = "C_Sex")
    private String sex;
    @Column(name = "C_Phno")
    private String phoneNo;
    @Column(name = "C_Mail")
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
