package com.grainindustries.mpesa_c2b_api.dto.requests;

public record C2bRegistrationCommand(
        String shortcode,
        String responseType,
        String confirmationUrl,
        String validationUrl
) {
}
