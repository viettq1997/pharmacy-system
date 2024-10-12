package com.app.pharmacy.controller;

import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeResponse;
import com.app.pharmacy.service.EmployeeService;
import com.app.pharmacy.service.KeycloakAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final KeycloakAdminService keycloakAdminService;
    private final EmployeeService employeeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CreateEmployeeResponse>> createEmployee(
            @Valid @RequestBody CreateEmployeeRequest request, Authentication connectedUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(request, connectedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteEmployee(@PathVariable("id") String id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
