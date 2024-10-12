package com.app.pharmacy.service;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeResponse;
import com.app.pharmacy.domain.entity.Employee;
import com.app.pharmacy.exception.CustomResponseException;
import com.app.pharmacy.exception.ErrorCode;
import com.app.pharmacy.mapper.EmployeeMapper;
import com.app.pharmacy.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final KeycloakAdminService keycloakAdminService;
    private final Clock clock;
    private final EmployeeRepository employeeRepository;

    public ApiResponse<CreateEmployeeResponse> createEmployee(CreateEmployeeRequest request, Authentication connectedUser) {
        ApiResponse<CreateEmployeeResponse> response = new ApiResponse<>();
        String userId = keycloakAdminService.createUser(request);
        Employee employee = EmployeeMapper.INSTANCE.toEntity(request);
        employee.setId(userId);
        employee.setJoinDate(LocalDateTime.now(clock));
        employee.setCreatedBy(connectedUser.getName());
        employee.setCreatedDate(LocalDateTime.now(clock));
        employeeRepository.save(employee);

        CreateEmployeeResponse createEmployeeResponse = EmployeeMapper.INSTANCE.toEmployeeResponse(employee);
        createEmployeeResponse.setUsername(request.username());
        response.setMessage("Created employee!");
        response.setData(createEmployeeResponse);
        return response;
    }

    public ApiResponse<?> deleteEmployee(String employeeId) {
        ApiResponse<?> response = new ApiResponse<>();
        employeeRepository.findById(employeeId).ifPresentOrElse(employeeRepository::delete, () -> {
            throw new CustomResponseException(ErrorCode.USER_NOT_EXIST);
        });
        response.setMessage("Deleted employee!");
        return response;
    }
}
