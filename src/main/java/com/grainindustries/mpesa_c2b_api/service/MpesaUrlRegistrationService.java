package com.grainindustries.mpesa_c2b_api.service;

import com.grainindustries.mpesa_c2b_api.config.DarajaProperties;
import com.grainindustries.mpesa_c2b_api.dto.DarajaApiResponse;
import com.grainindustries.mpesa_c2b_api.dto.DarajaRegisterUrlRequest;
import com.grainindustries.mpesa_c2b_api.dto.DarajaRegisterUrlResponse;
import com.grainindustries.mpesa_c2b_api.dto.requests.C2bRegistrationCommand;
import com.grainindustries.mpesa_c2b_api.sdk.DarajaSdk;
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
