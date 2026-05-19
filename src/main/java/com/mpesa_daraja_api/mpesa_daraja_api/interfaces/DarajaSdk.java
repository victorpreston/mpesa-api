package com.mpesa_daraja_api.mpesa_daraja_api.interfaces;

import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.AccountBalanceRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.B2bPaymentRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.B2cPaymentRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.C2bRegistrationCommand;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.C2bSimulationCommand;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.DynamicQrRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.ReversalRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.StkPushQueryRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.StkPushRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.TransactionStatusRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.DarajaApiResponse;

import java.util.Map;

public interface DarajaSdk {

    String accessToken();

    DarajaApiResponse stkPush(StkPushRequest request);

    DarajaApiResponse queryStkPush(StkPushQueryRequest request);

    DarajaApiResponse registerC2bUrls(C2bRegistrationCommand request);

    DarajaApiResponse simulateC2b(C2bSimulationCommand request);

    DarajaApiResponse b2c(B2cPaymentRequest request);

    DarajaApiResponse b2b(B2bPaymentRequest request);

    DarajaApiResponse transactionStatus(TransactionStatusRequest request);

    DarajaApiResponse accountBalance(AccountBalanceRequest request);

    DarajaApiResponse reverse(ReversalRequest request);

    DarajaApiResponse dynamicQr(DynamicQrRequest request);

    DarajaApiResponse createSingleBill(Map<String, Object> request);

    DarajaApiResponse createBulkBills(Map<String, Object> request);

    DarajaApiResponse cancelBill(Map<String, Object> request);

    DarajaApiResponse reconcileBill(Map<String, Object> request);
}
