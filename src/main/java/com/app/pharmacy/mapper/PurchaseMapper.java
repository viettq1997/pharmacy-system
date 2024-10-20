package com.app.pharmacy.mapper;

import com.app.pharmacy.domain.dto.purchase.CreatePurchaseRequest;
import com.app.pharmacy.domain.dto.purchase.CreateUpdatePurchaseResponse;
import com.app.pharmacy.domain.dto.purchase.GetPurchaseResponse;
import com.app.pharmacy.domain.dto.purchase.UpdatePurchaseRequest;
import com.app.pharmacy.domain.entity.Purchase;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    Purchase toEntity(CreatePurchaseRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(UpdatePurchaseRequest request, @MappingTarget Purchase purchase);
    CreateUpdatePurchaseResponse toCreateUpdateResponse(Purchase purchase);

    @Mapping(target = "medicine", source = "medicine")
    @Mapping(target = "medicine.createdBy", source = "medicine.employee.firstName")
    @Mapping(target = "supplier", source = "supplier")
    @Mapping(target = "supplier.createdBy", source = "supplier.employee.firstName")
    GetPurchaseResponse toGetPurchaseResponse(Purchase purchase);

    @Mapping(target = "createdBy", source = "employee.firstName")
    List<GetPurchaseResponse> toPurchaseList(List<Purchase> purchases);

    CreatePurchaseRequest toCreateRequest(UpdatePurchaseRequest request);
}
