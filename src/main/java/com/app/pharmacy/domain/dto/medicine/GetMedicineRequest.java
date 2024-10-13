package com.app.pharmacy.domain.dto.medicine;

public record GetMedicineRequest(String name, String category, Integer quantity) {
}
