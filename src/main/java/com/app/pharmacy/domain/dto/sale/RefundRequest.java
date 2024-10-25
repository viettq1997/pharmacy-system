package com.app.pharmacy.domain.dto.sale;

import jakarta.validation.constraints.NotBlank;

public record RefundRequest(
        @NotBlank(message = "refundItemId is mandatory field")
        String refundItemId,
        @NotBlank(message = "refundItemQuantity is mandatory field")
        String refundItemQuantity,
        String newItemId,
        String newItemQuantity
) {
}
