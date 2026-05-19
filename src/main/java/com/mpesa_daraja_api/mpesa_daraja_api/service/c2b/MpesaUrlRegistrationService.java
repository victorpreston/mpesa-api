package com.mpesa_daraja_api.mpesa_daraja_api.service.c2b;

import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.C2bRegistrationCommand;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.DarajaApiResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.interfaces.DarajaSdk;
import org.springframework.stereotype.Service;

@Service
public class MpesaUrlRegistrationService {

    private final DarajaSdk darajaSdk;
    private final DarajaProperties properties;

    public MpesaUrlRegistrationService(DarajaSdk darajaSdk, DarajaProperties properties) {
        this.darajaSdk = darajaSdk;
        this.properties = properties;
    }

    public DarajaApiResponse registerCallbackUrls() {
        return darajaSdk.registerC2bUrls(new C2bRegistrationCommand(
                properties.getShortcode(),
                properties.getResponseType(),
                properties.getConfirmationUrl(),
                properties.getValidationUrl()
        ));
    }
}
