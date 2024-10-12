package com.app.pharmacy.mapper;

import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.domain.dto.employee.EmployeeResponse;
import com.app.pharmacy.domain.dto.employee.UpdateEmployeeRequest;
import com.app.pharmacy.domain.entity.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEntity(CreateEmployeeRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(UpdateEmployeeRequest request, @MappingTarget Employee employee);

    EmployeeResponse toEmployeeResponse(Employee employee);
}
