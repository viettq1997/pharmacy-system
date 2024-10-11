package com.app.pharmacy.mapper;

import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeResponse;
import com.app.pharmacy.domain.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEntity(CreateEmployeeRequest request);
    CreateEmployeeResponse toEmployeeResponse(Employee employee);
}
