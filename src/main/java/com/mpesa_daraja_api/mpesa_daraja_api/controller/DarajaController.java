package com.mpesa_daraja_api.mpesa_daraja_api.controller;

import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.AccountBalanceRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.B2bPaymentRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.B2cPaymentRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.DynamicQrRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.ReversalRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.StkPushQueryRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.StkPushRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.TransactionStatusRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.DarajaApiResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.sdk.DarajaSdk;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/mpesa")
@CrossOrigin(origins = "${app.cors.allowed-origins:*}")
public class DarajaController {

    private final DarajaSdk darajaSdk;

    public DarajaController(DarajaSdk darajaSdk) {
        this.darajaSdk = darajaSdk;
    }

    @PostMapping("/stk-push")
    public ResponseEntity<DarajaApiResponse> stkPush(@Valid @RequestBody StkPushRequest request) {
        return ResponseEntity.ok(darajaSdk.stkPush(request));
    }

    @PostMapping("/stk-push/query")
    public ResponseEntity<DarajaApiResponse> queryStkPush(@Valid @RequestBody StkPushQueryRequest request) {
        return ResponseEntity.ok(darajaSdk.queryStkPush(request));
    }

    @PostMapping("/b2c/payment")
    public ResponseEntity<DarajaApiResponse> b2c(@Valid @RequestBody B2cPaymentRequest request) {
        return ResponseEntity.ok(darajaSdk.b2c(request));
    }

    @PostMapping("/b2b/payment")
    public ResponseEntity<DarajaApiResponse> b2b(@Valid @RequestBody B2bPaymentRequest request) {
        return ResponseEntity.ok(darajaSdk.b2b(request));
    }

    @PostMapping("/transactions/status")
    public ResponseEntity<DarajaApiResponse> transactionStatus(@Valid @RequestBody TransactionStatusRequest request) {
        return ResponseEntity.ok(darajaSdk.transactionStatus(request));
    }

    @PostMapping("/account-balance")
    public ResponseEntity<DarajaApiResponse> accountBalance(@Valid @RequestBody AccountBalanceRequest request) {
        return ResponseEntity.ok(darajaSdk.accountBalance(request));
    }

    @PostMapping("/reversal")
    public ResponseEntity<DarajaApiResponse> reverse(@Valid @RequestBody ReversalRequest request) {
        return ResponseEntity.ok(darajaSdk.reverse(request));
    }

    @PostMapping("/dynamic-qr")
    public ResponseEntity<DarajaApiResponse> dynamicQr(@Valid @RequestBody DynamicQrRequest request) {
        return ResponseEntity.ok(darajaSdk.dynamicQr(request));
    }

    @PostMapping("/bill-manager/invoices/single")
    public ResponseEntity<DarajaApiResponse> createSingleBill(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(darajaSdk.createSingleBill(request));
    }

    @PostMapping("/bill-manager/invoices/bulk")
    public ResponseEntity<DarajaApiResponse> createBulkBills(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(darajaSdk.createBulkBills(request));
    }

    @PostMapping("/bill-manager/invoices/cancel")
    public ResponseEntity<DarajaApiResponse> cancelBill(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(darajaSdk.cancelBill(request));
    }

    @PostMapping("/bill-manager/reconciliation")
    public ResponseEntity<DarajaApiResponse> reconcileBill(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(darajaSdk.reconcileBill(request));
    }
}
