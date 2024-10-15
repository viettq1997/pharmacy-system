package com.app.pharmacy.mapper;

import com.app.pharmacy.domain.dto.medicinecategory.CreateMedicineCategoryRequest;
import com.app.pharmacy.domain.dto.medicinecategory.MedicineCategoryResponse;
import com.app.pharmacy.domain.dto.medicinecategory.UpdateMedicineCategoryRequest;
import com.app.pharmacy.domain.entity.MedicineCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicineCategoryMapper {

    MedicineCategoryMapper INSTANCE = Mappers.getMapper(MedicineCategoryMapper.class);

    MedicineCategory toEntity(CreateMedicineCategoryRequest request);
    void toEntity(UpdateMedicineCategoryRequest request, @MappingTarget MedicineCategory medicinecategory);
    MedicineCategoryResponse toMedicineCategoryResponse(MedicineCategory medicinecategory);
    List<MedicineCategoryResponse> toListMedicineCategoryResponse(List<MedicineCategory> medicineCategories);
}
