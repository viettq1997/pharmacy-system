package com.app.pharmacy.specification;

import com.app.pharmacy.domain.entity.Medicine;
import com.app.pharmacy.domain.entity.Purchase;
import com.app.pharmacy.domain.entity.Supplier;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class PurchaseSpecifications {

    public static Specification<Purchase> hasMedicineName(String medName) {
        return (root, query, criteriaBuilder) -> {

            Join<Purchase, Medicine> medicineJoin = root.join("medicine", JoinType.INNER);

            if (medName == null || medName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(medicineJoin.get("name")), "%" + medName.toLowerCase() + "%");
        };
    }

    public static Specification<Purchase> hasSupplierName(String supplierName) {
        return (root, query, criteriaBuilder) -> {

            Join<Purchase, Supplier> supplierJoin = root.join("supplierId", JoinType.INNER);

            if (supplierName == null || supplierName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(supplierJoin.get("name")), "%" + supplierName.toLowerCase() + "%");
        };
    }
}
