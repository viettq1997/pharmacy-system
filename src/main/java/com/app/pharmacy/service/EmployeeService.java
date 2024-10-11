package com.app.pharmacy.service;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeResponse;
import com.app.pharmacy.domain.entity.Employee;
import com.app.pharmacy.mapper.EmployeeMapper;
import com.app.pharmacy.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final KeycloakAdminService keycloakAdminService;
    private final Clock clock;
    private final EmployeeRepository employeeRepository;

    public ApiResponse<CreateEmployeeResponse> createEmployee(CreateEmployeeRequest request) {
        ApiResponse<CreateEmployeeResponse> response = new ApiResponse<>();
        String userId = keycloakAdminService.createUser(request);
        Employee employee = EmployeeMapper.INSTANCE.toEntity(request);
        employee.setId(userId);
        employee.setJoinDate(LocalDateTime.now(clock));
        employeeRepository.save(employee);

        CreateEmployeeResponse createEmployeeResponse = EmployeeMapper.INSTANCE.toEmployeeResponse(employee);
        createEmployeeResponse.setUsername(request.username());
        response.setMessage("Created employee!");
        response.setData(createEmployeeResponse);
        return response;
    }
}
