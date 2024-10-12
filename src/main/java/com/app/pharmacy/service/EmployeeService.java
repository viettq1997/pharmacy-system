package com.app.pharmacy.service;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.domain.dto.employee.EmployeeResponse;
import com.app.pharmacy.domain.dto.employee.GetEmployeeRequest;
import com.app.pharmacy.domain.dto.employee.UpdateEmployeeRequest;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final KeycloakAdminService keycloakAdminService;
    private final Clock clock;
    private final EmployeeRepository employeeRepository;

    public ApiResponse<EmployeeResponse> createEmployee(
            CreateEmployeeRequest request, Authentication connectedUser) {

        ApiResponse<EmployeeResponse> response = new ApiResponse<>();
        String userId = keycloakAdminService.createUser(request);
        Employee employee = EmployeeMapper.INSTANCE.toEntity(request);
        employee.setId(userId);
        employee.setJoinDate(LocalDateTime.now(clock));
        employee.setCreatedBy(connectedUser.getName());
        employee.setCreatedDate(LocalDateTime.now(clock));
        employeeRepository.save(employee);

        EmployeeResponse employeeResponse = EmployeeMapper.INSTANCE.toEmployeeResponse(employee);
        employeeResponse.setUsername(request.username());
        response.setMessage("Created employee!");
        response.setData(employeeResponse);
        return response;
    }

    public ApiResponse<List<EmployeeResponse>> getEmployees(GetEmployeeRequest request, Authentication connectedUser) {
        ApiResponse<List<EmployeeResponse>> response = new ApiResponse<>();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        response.setData(employeeResponses);
        return response;
    }

    public ApiResponse<EmployeeResponse> updateEmployee(
            String employeeId, UpdateEmployeeRequest request, Authentication connectedUser) {
        ApiResponse<EmployeeResponse> response = new ApiResponse<>();
        employeeRepository.findById(employeeId).ifPresentOrElse(employee -> {
            if (!request.username().equals(employee.getUsername())) {
                throw new CustomResponseException(ErrorCode.USERNAME_CANNOT_CHANGE);
            }
            EmployeeMapper.INSTANCE.toEntity(request, employee);
            employeeRepository.save(employee);

            EmployeeResponse employeeResponse = EmployeeMapper.INSTANCE.toEmployeeResponse(employee);
            employeeResponse.setUsername(request.username());
            response.setMessage("Updated employee!");
            response.setData(employeeResponse);
        }, () -> {
            throw new CustomResponseException(ErrorCode.USER_NOT_EXIST);
        });

        return response;
    }

    public ApiResponse<?> deleteEmployee(String employeeId) {
        ApiResponse<?> response = new ApiResponse<>();
        keycloakAdminService.deleteUser(employeeId);
        employeeRepository.findById(employeeId).ifPresentOrElse(employeeRepository::delete, () -> {
            throw new CustomResponseException(ErrorCode.USER_NOT_EXIST);
        });
        response.setMessage("Deleted employee!");
        return response;
    }
}
