package com.app.pharmacy.service;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.common.CommonGetResponse;
import com.app.pharmacy.domain.dto.inventory.GetInventoryRequest;
import com.app.pharmacy.domain.dto.inventory.InventoryDto;
import com.app.pharmacy.domain.entity.Inventory;
import com.app.pharmacy.mapper.InventoryMapper;
import com.app.pharmacy.repository.InventoryRepository;
import com.app.pharmacy.specification.InventorySpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.app.pharmacy.specification.InventorySpecifications.hasExpireDate;
import static com.app.pharmacy.specification.InventorySpecifications.hasMedicineName;
import static com.app.pharmacy.specification.InventorySpecifications.hasQuantity;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public ApiResponse<CommonGetResponse<InventoryDto>> getInventory(GetInventoryRequest request, Pageable pageable) {
        ApiResponse<CommonGetResponse<InventoryDto>> response = new ApiResponse<>();
        Specification<Inventory> specification = Specification.where(
                hasMedicineName(request.medicineName())
                        .and(hasQuantity(request.quantity()))
                        .and(hasExpireDate(request.expireDateBegin(), request.expireDateEnd()))
        );
        Page<Inventory> inventoryPage = inventoryRepository.findAll(specification, pageable);
        List<InventoryDto> inventoryDtos = InventoryMapper.INSTANCE.toDtos(inventoryPage.getContent());
        response.setData(new CommonGetResponse<>(
                inventoryDtos,
                inventoryPage.getSize(),
                inventoryPage.getNumber(),
                inventoryPage.getTotalElements()
        ));
        return response;
    }
}
