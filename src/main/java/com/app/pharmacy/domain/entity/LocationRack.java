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
@Table(name = "LOCATION_RACK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationRack {

    @Id
    @Column(name = "LR_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "LR_POSITION")
    private String position;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "updated_by")
    private String updatedBy;
}
