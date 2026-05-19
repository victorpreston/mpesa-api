package com.mpesa_daraja_api.mpesa_daraja_api.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record C2bSimulationCommand(
        String shortcode,
        String commandId,
        @DecimalMin(value = "1.0") BigDecimal amount,
        @NotBlank String phoneNumber,
        String billRefNumber
) {
}
