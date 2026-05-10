package com.grainindustries.mpesa_c2b_api.sdk;

import com.grainindustries.mpesa_c2b_api.dto.DarajaApiResponse;
import com.grainindustries.mpesa_c2b_api.dto.requests.AccountBalanceRequest;
import com.grainindustries.mpesa_c2b_api.dto.requests.B2bPaymentRequest;
import com.grainindustries.mpesa_c2b_api.dto.requests.B2cPaymentRequest;
import com.grainindustries.mpesa_c2b_api.dto.requests.C2bRegistrationCommand;
import com.grainindustries.mpesa_c2b_api.dto.requests.C2bSimulationCommand;
import com.grainindustries.mpesa_c2b_api.dto.requests.DynamicQrRequest;
import com.grainindustries.mpesa_c2b_api.dto.requests.ReversalRequest;
import com.grainindustries.mpesa_c2b_api.dto.requests.StkPushQueryRequest;
import com.grainindustries.mpesa_c2b_api.dto.requests.StkPushRequest;
import com.grainindustries.mpesa_c2b_api.dto.requests.TransactionStatusRequest;

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
