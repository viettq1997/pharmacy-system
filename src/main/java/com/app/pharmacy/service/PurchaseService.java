package com.app.pharmacy.service;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.dto.purchase.CreatePurchaseRequest;
import com.app.pharmacy.domain.dto.purchase.CreateUpdatePurchaseResponse;
import com.app.pharmacy.domain.entity.Purchase;
import com.app.pharmacy.mapper.PurchaseMapper;
import com.app.pharmacy.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final Clock clock;

    public ApiResponse<CreateUpdatePurchaseResponse> createPurchase(CreatePurchaseRequest request, Authentication connectedUser) {
        ApiResponse<CreateUpdatePurchaseResponse> response = new ApiResponse<>();
        Purchase purchase = PurchaseMapper.INSTANCE.toEntity(request);
        purchase.setCreatedBy(connectedUser.getName());
        purchase.setCreatedDate(LocalDateTime.now(clock));
        purchaseRepository.save(purchase);

        CreateUpdatePurchaseResponse purchaseResponse = PurchaseMapper.INSTANCE.toCreateUpdateResponse(purchase);
        response.setData(purchaseResponse);
        return response;
    }
}
