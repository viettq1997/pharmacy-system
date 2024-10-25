package com.app.pharmacy.controller;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.common.CommonGetResponse;
import com.app.pharmacy.domain.dto.inventory.GetInventoryRequest;
import com.app.pharmacy.domain.dto.inventory.InventoryDto;
import com.app.pharmacy.domain.dto.sale.SaleLogRequest;
import com.app.pharmacy.domain.dto.sale.SaleResponse;
import com.app.pharmacy.service.InventoryService;
import com.app.pharmacy.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final SaleService saleService;
    private final InventoryService inventoryService;

    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<CommonGetResponse<SaleResponse>>> reportSales(
            @ModelAttribute SaleLogRequest request,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication connectedUser
            ) {
        return ResponseEntity.ok(saleService.getSales(request, pageable, connectedUser));
    }

    @GetMapping("/inventory")
    public ResponseEntity<ApiResponse<CommonGetResponse<InventoryDto>>> reportInventory(
            @ModelAttribute GetInventoryRequest request,
            @PageableDefault(sort = "expDate", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(inventoryService.getInventory(request, pageable));
    }
}
