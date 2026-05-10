package com.grainindustries.mpesa_c2b_api.service;

import com.grainindustries.mpesa_c2b_api.config.DarajaProperties;
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
import com.grainindustries.mpesa_c2b_api.sdk.DarajaClient;
import com.grainindustries.mpesa_c2b_api.sdk.DarajaSdk;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DarajaSdkService implements DarajaSdk {

    private final MpesaAuthService authService;
    private final DarajaClient client;
    private final DarajaPayloadFactory payloadFactory;
    private final DarajaProperties.Endpoints endpoints;

    public DarajaSdkService(
            MpesaAuthService authService,
            DarajaClient client,
            DarajaPayloadFactory payloadFactory,
            DarajaProperties properties) {
        this.authService = authService;
        this.client = client;
        this.payloadFactory = payloadFactory;
        this.endpoints = properties.getEndpoints();
    }

    @Override
    public String accessToken() {
        return authService.generateAccessToken();
    }

    @Override
    public DarajaApiResponse stkPush(StkPushRequest request) {
        return client.post(endpoints.getStkPush(), payloadFactory.stkPush(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse queryStkPush(StkPushQueryRequest request) {
        return client.post(endpoints.getStkPushQuery(), payloadFactory.stkPushQuery(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse registerC2bUrls(C2bRegistrationCommand request) {
        return client.post(endpoints.getC2bRegister(), payloadFactory.c2bRegistration(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse simulateC2b(C2bSimulationCommand request) {
        return client.post(endpoints.getC2bSimulate(), payloadFactory.c2bSimulation(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse b2c(B2cPaymentRequest request) {
        return client.post(endpoints.getB2c(), payloadFactory.b2c(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse b2b(B2bPaymentRequest request) {
        return client.post(endpoints.getB2b(), payloadFactory.b2b(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse transactionStatus(TransactionStatusRequest request) {
        return client.post(endpoints.getTransactionStatus(), payloadFactory.transactionStatus(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse accountBalance(AccountBalanceRequest request) {
        return client.post(endpoints.getAccountBalance(), payloadFactory.accountBalance(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse reverse(ReversalRequest request) {
        return client.post(endpoints.getReversal(), payloadFactory.reversal(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse dynamicQr(DynamicQrRequest request) {
        return client.post(endpoints.getDynamicQr(), payloadFactory.dynamicQr(request), DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse createSingleBill(Map<String, Object> request) {
        return client.post(endpoints.getBillManagerSingleInvoice(), request, DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse createBulkBills(Map<String, Object> request) {
        return client.post(endpoints.getBillManagerBulkInvoice(), request, DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse cancelBill(Map<String, Object> request) {
        return client.post(endpoints.getBillManagerCancelInvoice(), request, DarajaApiResponse.class);
    }

    @Override
    public DarajaApiResponse reconcileBill(Map<String, Object> request) {
        return client.post(endpoints.getBillManagerReconciliation(), request, DarajaApiResponse.class);
    }
}
