package com.grainindustries.mpesa_c2b_api.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record DynamicQrRequest(
        @NotBlank String merchantName,
        @NotBlank String refNo,
        @DecimalMin(value = "1.0") BigDecimal amount,
        @NotBlank String trxCode,
        @NotBlank String cpi,
        String size
) {
}
