package com.mpesa_daraja_api.mpesa_daraja_api.service;

import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.DarajaApiResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.DarajaRegisterUrlRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.DarajaRegisterUrlResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.requests.C2bRegistrationCommand;
import com.mpesa_daraja_api.mpesa_daraja_api.sdk.DarajaSdk;
import org.springframework.stereotype.Service;

@Service
public class MpesaUrlRegistrationService {

    private final DarajaSdk darajaSdk;
    private final DarajaProperties properties;

    public MpesaUrlRegistrationService(DarajaSdk darajaSdk, DarajaProperties properties) {
        this.darajaSdk = darajaSdk;
        this.properties = properties;
    }

    public DarajaRegisterUrlResponse registerCallbackUrls() {
        DarajaApiResponse response = registerCallbackUrls(new DarajaRegisterUrlRequest(
                properties.getShortcode(),
                properties.getResponseType(),
                properties.getConfirmationUrl(),
                properties.getValidationUrl()
        ));
        return toLegacyResponse(response);
    }

    public DarajaApiResponse registerCallbackUrls(DarajaRegisterUrlRequest request) {
        return darajaSdk.registerC2bUrls(new C2bRegistrationCommand(
                request.getShortCode(),
                request.getResponseType(),
                request.getConfirmationURL(),
                request.getValidationURL()
        ));
    }

    private DarajaRegisterUrlResponse toLegacyResponse(DarajaApiResponse response) {
        return new DarajaRegisterUrlResponse(
                response.getOriginatorConversationId(),
                response.getResponseCode(),
                response.getResponseDescription()
        );
    }
}
