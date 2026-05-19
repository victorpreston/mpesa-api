package com.mpesa_daraja_api.mpesa_daraja_api.service.c2b;

import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.C2bSimulationCommand;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.DarajaApiResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.interfaces.DarajaSdk;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MpesaSimulationService {

    private final DarajaSdk darajaSdk;
    private final DarajaProperties properties;

    public MpesaSimulationService(DarajaSdk darajaSdk, DarajaProperties properties) {
        this.darajaSdk = darajaSdk;
        this.properties = properties;
    }

    public DarajaApiResponse simulateTransaction(String commandID, String amount,
                                                 String phoneNumber, String billRefNumber) {
        return simulateTransaction(properties.getShortcode(), commandID, amount, phoneNumber, billRefNumber);
    }

    public DarajaApiResponse simulateTransaction(String shortCode, String commandID,
                                                 String amount, String phoneNumber,
                                                 String billRefNumber) {
        validateInput(commandID, amount, phoneNumber);
        return darajaSdk.simulateC2b(new C2bSimulationCommand(
                shortCode,
                commandID,
                new BigDecimal(amount),
                phoneNumber,
                billRefNumber != null ? billRefNumber : ""
        ));
    }

    private void validateInput(String commandID, String amount, String phoneNumber) {
        if (!isValidCommandID(commandID)) {
            throw new IllegalArgumentException(
                "Invalid CommandID. Must be CustomerPayBillOnline or CustomerBuyGoodsOnline"
            );
        }
        if (!isValidAmount(amount)) {
            throw new IllegalArgumentException("Amount must be a valid number greater than 0");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException(
                "Invalid phone number. Must start with 254 and be 12 digits long"
            );
        }
    }

    private boolean isValidCommandID(String commandID) {
        return commandID != null && (
            commandID.equals("CustomerPayBillOnline") ||
            commandID.equals("CustomerBuyGoodsOnline")
        );
    }

    private boolean isValidAmount(String amount) {
        try {
            double value = Double.parseDouble(amount);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^254\\d{9}$");
    }
}
