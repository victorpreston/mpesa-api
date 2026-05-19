package com.mpesa_daraja_api.mpesa_daraja_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record C2bSimulationCommand(
        String shortcode,
        String commandId,
        @DecimalMin(value = "1.0") BigDecimal amount,
        @NotBlank @Pattern(regexp = "^254\\d{9}$", message = "Phone number must start with 254 and be 12 digits") String phoneNumber,
        String billRefNumber
) {
}
