package com.app.pharmacy.repository;

import com.app.pharmacy.domain.entity.LocationRack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRackRepository extends JpaRepository<LocationRack, String>, JpaSpecificationExecutor<LocationRack> {
}
