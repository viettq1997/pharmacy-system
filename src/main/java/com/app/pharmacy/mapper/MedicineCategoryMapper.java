package com.app.pharmacy.mapper;

import com.app.pharmacy.domain.dto.medicinecategory.CreateMedicineCategoryRequest;
import com.app.pharmacy.domain.dto.medicinecategory.MedicineCategoryResponse;
import com.app.pharmacy.domain.dto.medicinecategory.UpdateMedicineCategoryRequest;
import com.app.pharmacy.domain.entity.MedicineCategory;
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
public interface MedicineCategoryMapper {

    MedicineCategoryMapper INSTANCE = Mappers.getMapper(MedicineCategoryMapper.class);

    MedicineCategory toEntity(CreateMedicineCategoryRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(UpdateMedicineCategoryRequest request, @MappingTarget MedicineCategory medicinecategory);
    @Mapping(target = "createdBy", source = "employeeCreated.firstName")
    @Mapping(target = "updatedBy", source = "employeeUpdated.firstName")
    MedicineCategoryResponse toMedicineCategoryResponse(MedicineCategory medicinecategory);
    List<MedicineCategoryResponse> toListMedicineCategoryResponse(List<MedicineCategory> medicineCategories);
}
