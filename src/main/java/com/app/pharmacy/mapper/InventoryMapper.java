package com.app.pharmacy.mapper;

import com.app.pharmacy.domain.dto.inventory.InventoryDto;
import com.app.pharmacy.domain.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InventoryMapper {

    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    @Mapping(target = "position", source = "locationRack.position")
    @Mapping(target = "createdBy", source = "employeeCreated.firstName")
    @Mapping(target = "updatedBy", source = "employeeUpdated.firstName")
    @Mapping(target = "medicineName", source = "medicine.name")
    InventoryDto toDto(Inventory inventory);

    List<InventoryDto> toDtos(List<Inventory> inventories);
}
