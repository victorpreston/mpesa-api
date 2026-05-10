package com.grainindustries.mpesa_c2b_api.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record StkPushQueryRequest(
        @NotBlank String checkoutRequestId,
        String shortcode
) {
}
