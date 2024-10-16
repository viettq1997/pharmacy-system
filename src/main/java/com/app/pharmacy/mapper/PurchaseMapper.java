package com.app.pharmacy.mapper;

import com.app.pharmacy.domain.dto.purchase.CreatePurchaseRequest;
import com.app.pharmacy.domain.dto.purchase.CreateUpdatePurchaseResponse;
import com.app.pharmacy.domain.dto.purchase.GetPurchaseResponse;
import com.app.pharmacy.domain.dto.purchase.UpdatePurchaseRequest;
import com.app.pharmacy.domain.entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    Purchase toEntity(CreatePurchaseRequest request);
    void toEntity(UpdatePurchaseRequest request, @MappingTarget Purchase purchase);
    CreateUpdatePurchaseResponse toCreateUpdateResponse(Purchase purchase);
    List<GetPurchaseResponse> toPurchaseList(List<Purchase> purchases);
}
