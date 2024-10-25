package com.app.pharmacy.controller;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.common.CommonGetResponse;
import com.app.pharmacy.domain.dto.sale.CreateSaleRequest;
import com.app.pharmacy.domain.dto.sale.SaleResponse;
import com.app.pharmacy.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public ResponseEntity<ApiResponse<SaleResponse>> createSale(
            @RequestBody CreateSaleRequest request, Authentication connectedUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.createSale(request, connectedUser));
    }

//    @PostMapping("/refund")
//    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
//    public ResponseEntity<ApiResponse<RefundResponse>> refund(
//            @RequestBody RefundRequest request, Authentication connectedUser
//            ) {
//
//    }
}
