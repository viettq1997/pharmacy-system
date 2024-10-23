package com.app.pharmacy.repository;

import com.app.pharmacy.domain.entity.CustomerPointConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPointConfigRepository extends JpaRepository<CustomerPointConfig, String> {
}
