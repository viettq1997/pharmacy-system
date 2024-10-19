package com.app.pharmacy.repository;

import com.app.pharmacy.domain.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findByMedicineIdAndMfgDate(String medicineId, LocalDate mfgDate);
}
