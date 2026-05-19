package com.mpesa_daraja_api.mpesa_daraja_api.dto.requests;

public record C2bRegistrationCommand(
        String shortcode,
        String responseType,
        String confirmationUrl,
        String validationUrl
) {
}
