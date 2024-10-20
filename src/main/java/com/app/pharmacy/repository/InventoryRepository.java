package com.app.pharmacy.repository;

import com.app.pharmacy.domain.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String>, JpaSpecificationExecutor<Inventory> {
    Optional<Inventory> findByMedicineIdAndMfgDate(String medicineId, LocalDate mfgDate);
    List<Inventory> findByIdIn(List<String> ids);
}
