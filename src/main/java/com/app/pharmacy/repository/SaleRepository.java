package com.app.pharmacy.repository;

import com.app.pharmacy.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String>, JpaSpecificationExecutor<Sale> {
    boolean existsByCustomerId(String customerId);
}
