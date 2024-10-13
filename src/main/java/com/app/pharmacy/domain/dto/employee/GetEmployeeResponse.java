package com.app.pharmacy.domain.dto.employee;

import java.util.List;

public record GetEmployeeResponse(List<EmployeeResponse> content, int size, int number, long totalElement) {
}
