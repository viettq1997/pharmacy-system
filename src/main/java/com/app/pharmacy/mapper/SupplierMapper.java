package com.app.pharmacy.mapper;

import com.app.pharmacy.domain.dto.supplier.CreateSupplierRequest;
import com.app.pharmacy.domain.dto.supplier.SupplierResponse;
import com.app.pharmacy.domain.dto.supplier.UpdateSupplierRequest;
import com.app.pharmacy.domain.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SupplierMapper {

    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    Supplier toEntity(CreateSupplierRequest request);
    void toEntity(UpdateSupplierRequest request, @MappingTarget Supplier supplier);
    SupplierResponse toSupplierResponse(Supplier supplier);
    List<SupplierResponse> toListSupplierResponse(List<Supplier> suppliers);
}
