package com.app.pharmacy.mapper;

import com.app.pharmacy.domain.dto.medicine.CreateMedicineRequest;
import com.app.pharmacy.domain.dto.medicine.MedUnitResponse;
import com.app.pharmacy.domain.dto.medicine.MedicineResponse;
import com.app.pharmacy.domain.dto.medicine.UpdateMedicineRequest;
import com.app.pharmacy.domain.entity.Medicine;
import com.app.pharmacy.domain.entity.MedicineUnit;
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
public interface MedicineMapper {

    MedicineMapper INSTANCE = Mappers.getMapper(MedicineMapper.class);

    Medicine toEntity(CreateMedicineRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(UpdateMedicineRequest request, @MappingTarget Medicine medicine);
    @Mapping(target = "createdBy", source = "employeeCreated.firstName")
    @Mapping(target = "updatedBy", source = "employeeUpdated.firstName")
    MedicineResponse toMedicineResponse(Medicine medicine);
    List<MedicineResponse> toListMedicineResponse(List<Medicine> medicines);

    MedUnitResponse toMedicineUnitResponse(MedicineUnit medicineUnit);
    List<MedUnitResponse> toListMedicineUnitResponse(List<MedicineUnit> medicineUnits);
}
