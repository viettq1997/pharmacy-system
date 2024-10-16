package com.app.pharmacy.controller;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.dto.purchase.CreatePurchaseRequest;
import com.app.pharmacy.domain.dto.purchase.CreateUpdatePurchaseResponse;
import com.app.pharmacy.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CreateUpdatePurchaseResponse>> createPurchase(
            @Valid @RequestBody CreatePurchaseRequest request, Authentication connectedUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.createPurchase(request, connectedUser));
    }
}
