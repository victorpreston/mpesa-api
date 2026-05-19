package com.mpesa_daraja_api.mpesa_daraja_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record StkPushQueryRequest(
        @NotBlank String checkoutRequestId,
        String shortcode
) {
}
