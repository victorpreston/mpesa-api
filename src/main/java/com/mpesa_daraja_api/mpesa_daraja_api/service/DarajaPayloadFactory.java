package com.mpesa_daraja_api.mpesa_daraja_api.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
public class DarajaPayloadFactory {

    private static final DateTimeFormatter DARAJA_TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final DarajaProperties properties;
    private final Clock clock;

    public DarajaPayloadFactory(DarajaProperties properties, Clock clock) {
        this.properties = properties;
        this.clock = clock;
    }

    public StkPushPayload stkPush(StkPushRequest request) {
        String shortcode = valueOrDefault(request.shortcode(), properties.lipaShortcodeOrDefault());
        String timestamp = timestamp();
        return new StkPushPayload(
                shortcode,
                stkPassword(shortcode, timestamp),
                timestamp,
                valueOrDefault(request.transactionType(), "CustomerPayBillOnline"),
                amount(request.amount()),
                request.phoneNumber(),
                shortcode,
                request.phoneNumber(),
                valueOrDefault(request.callbackUrl(), properties.callbackUrlOrDefault()),
                valueOrDefault(request.accountReference(), "MPESA"),
                valueOrDefault(request.transactionDescription(), "M-PESA payment")
        );
    }

    public StkPushQueryPayload stkPushQuery(StkPushQueryRequest request) {
        String shortcode = valueOrDefault(request.shortcode(), properties.lipaShortcodeOrDefault());
        String timestamp = timestamp();
        return new StkPushQueryPayload(shortcode, stkPassword(shortcode, timestamp), timestamp, request.checkoutRequestId());
    }

    public C2bRegistrationPayload c2bRegistration(C2bRegistrationCommand request) {
        return new C2bRegistrationPayload(
                valueOrDefault(request.shortcode(), properties.getShortcode()),
                valueOrDefault(request.responseType(), properties.getResponseType()),
                valueOrDefault(request.confirmationUrl(), properties.getConfirmationUrl()),
                valueOrDefault(request.validationUrl(), properties.getValidationUrl())
        );
    }

    public C2bSimulationPayload c2bSimulation(C2bSimulationCommand request) {
        return new C2bSimulationPayload(
                valueOrDefault(request.shortcode(), properties.getShortcode()),
                valueOrDefault(request.commandId(), "CustomerPayBillOnline"),
                amount(request.amount()),
                request.phoneNumber(),
                valueOrDefault(request.billRefNumber(), "")
        );
    }

    public B2cPayload b2c(B2cPaymentRequest request) {
        return new B2cPayload(
                valueOrDefault(request.initiatorName(), properties.getInitiatorName()),
                valueOrDefault(request.securityCredential(), properties.getSecurityCredential()),
                valueOrDefault(request.commandId(), "BusinessPayment"),
                amount(request.amount()),
                valueOrDefault(request.partyA(), properties.getShortcode()),
                request.partyB(),
                valueOrDefault(request.remarks(), "B2C payment"),
                valueOrDefault(request.queueTimeoutUrl(), properties.getTimeoutUrl()),
                valueOrDefault(request.resultUrl(), properties.getResultUrl()),
                valueOrDefault(request.occasion(), "")
        );
    }

    public B2bPayload b2b(B2bPaymentRequest request) {
        return new B2bPayload(
                valueOrDefault(request.initiatorName(), properties.getInitiatorName()),
                valueOrDefault(request.securityCredential(), properties.getSecurityCredential()),
                valueOrDefault(request.commandId(), "BusinessPayBill"),
                valueOrDefault(request.senderIdentifierType(), "4"),
                valueOrDefault(request.receiverIdentifierType(), "4"),
                amount(request.amount()),
                valueOrDefault(request.partyA(), properties.getShortcode()),
                request.partyB(),
                valueOrDefault(request.accountReference(), "MPESA"),
                valueOrDefault(request.remarks(), "B2B payment"),
                valueOrDefault(request.queueTimeoutUrl(), properties.getTimeoutUrl()),
                valueOrDefault(request.resultUrl(), properties.getResultUrl())
        );
    }

    public TransactionStatusPayload transactionStatus(TransactionStatusRequest request) {
        return new TransactionStatusPayload(
                valueOrDefault(request.initiatorName(), properties.getInitiatorName()),
                valueOrDefault(request.securityCredential(), properties.getSecurityCredential()),
                "TransactionStatusQuery",
                request.transactionId(),
                valueOrDefault(request.partyA(), properties.getShortcode()),
                valueOrDefault(request.identifierType(), "4"),
                valueOrDefault(request.resultUrl(), properties.getResultUrl()),
                valueOrDefault(request.queueTimeoutUrl(), properties.getTimeoutUrl()),
                valueOrDefault(request.remarks(), "Transaction status query"),
                valueOrDefault(request.occasion(), "")
        );
    }

    public AccountBalancePayload accountBalance(AccountBalanceRequest request) {
        return new AccountBalancePayload(
                valueOrDefault(request.initiatorName(), properties.getInitiatorName()),
                valueOrDefault(request.securityCredential(), properties.getSecurityCredential()),
                "AccountBalance",
                valueOrDefault(request.partyA(), properties.getShortcode()),
                valueOrDefault(request.identifierType(), "4"),
                valueOrDefault(request.resultUrl(), properties.getResultUrl()),
                valueOrDefault(request.queueTimeoutUrl(), properties.getTimeoutUrl()),
                valueOrDefault(request.remarks(), "Account balance query")
        );
    }

    public ReversalPayload reversal(ReversalRequest request) {
        return new ReversalPayload(
                valueOrDefault(request.initiatorName(), properties.getInitiatorName()),
                valueOrDefault(request.securityCredential(), properties.getSecurityCredential()),
                "TransactionReversal",
                request.transactionId(),
                amount(request.amount()),
                valueOrDefault(request.receiverParty(), properties.getShortcode()),
                valueOrDefault(request.receiverIdentifierType(), "4"),
                valueOrDefault(request.resultUrl(), properties.getResultUrl()),
                valueOrDefault(request.queueTimeoutUrl(), properties.getTimeoutUrl()),
                valueOrDefault(request.remarks(), "Transaction reversal"),
                valueOrDefault(request.occasion(), "")
        );
    }

    public DynamicQrPayload dynamicQr(DynamicQrRequest request) {
        return new DynamicQrPayload(
                request.merchantName(),
                request.refNo(),
                amount(request.amount()),
                request.trxCode(),
                request.cpi(),
                valueOrDefault(request.size(), "300")
        );
    }

    String stkPassword(String shortcode, String timestamp) {
        String rawPassword = shortcode + valueOrDefault(properties.getPasskey(), "") + timestamp;
        return Base64.getEncoder().encodeToString(rawPassword.getBytes(StandardCharsets.UTF_8));
    }

    private String timestamp() {
        return LocalDateTime.now(clock).format(DARAJA_TIMESTAMP);
    }

    private static String amount(BigDecimal amount) {
        return amount.setScale(0, RoundingMode.HALF_UP).toPlainString();
    }

    private static String valueOrDefault(String value, String defaultValue) {
        return StringUtils.hasText(value) ? value : defaultValue;
    }

    public record StkPushPayload(
            @JsonProperty("BusinessShortCode") String businessShortCode,
            @JsonProperty("Password") String password,
            @JsonProperty("Timestamp") String timestamp,
            @JsonProperty("TransactionType") String transactionType,
            @JsonProperty("Amount") String amount,
            @JsonProperty("PartyA") String partyA,
            @JsonProperty("PartyB") String partyB,
            @JsonProperty("PhoneNumber") String phoneNumber,
            @JsonProperty("CallBackURL") String callbackUrl,
            @JsonProperty("AccountReference") String accountReference,
            @JsonProperty("TransactionDesc") String transactionDescription
    ) {
    }

    public record StkPushQueryPayload(
            @JsonProperty("BusinessShortCode") String businessShortCode,
            @JsonProperty("Password") String password,
            @JsonProperty("Timestamp") String timestamp,
            @JsonProperty("CheckoutRequestID") String checkoutRequestId
    ) {
    }

    public record C2bRegistrationPayload(
            @JsonProperty("ShortCode") String shortCode,
            @JsonProperty("ResponseType") String responseType,
            @JsonProperty("ConfirmationURL") String confirmationUrl,
            @JsonProperty("ValidationURL") String validationUrl
    ) {
    }

    public record C2bSimulationPayload(
            @JsonProperty("ShortCode") String shortCode,
            @JsonProperty("CommandID") String commandId,
            @JsonProperty("Amount") String amount,
            @JsonProperty("Msisdn") String msisdn,
            @JsonProperty("BillRefNumber") String billRefNumber
    ) {
    }

    public record B2cPayload(
            @JsonProperty("InitiatorName") String initiatorName,
            @JsonProperty("SecurityCredential") String securityCredential,
            @JsonProperty("CommandID") String commandId,
            @JsonProperty("Amount") String amount,
            @JsonProperty("PartyA") String partyA,
            @JsonProperty("PartyB") String partyB,
            @JsonProperty("Remarks") String remarks,
            @JsonProperty("QueueTimeOutURL") String queueTimeOutUrl,
            @JsonProperty("ResultURL") String resultUrl,
            @JsonProperty("Occassion") String occasion
    ) {
    }

    public record B2bPayload(
            @JsonProperty("Initiator") String initiator,
            @JsonProperty("SecurityCredential") String securityCredential,
            @JsonProperty("CommandID") String commandId,
            @JsonProperty("SenderIdentifierType") String senderIdentifierType,
            @JsonProperty("RecieverIdentifierType") String recieverIdentifierType,
            @JsonProperty("Amount") String amount,
            @JsonProperty("PartyA") String partyA,
            @JsonProperty("PartyB") String partyB,
            @JsonProperty("AccountReference") String accountReference,
            @JsonProperty("Remarks") String remarks,
            @JsonProperty("QueueTimeOutURL") String queueTimeOutUrl,
            @JsonProperty("ResultURL") String resultUrl
    ) {
    }

    public record TransactionStatusPayload(
            @JsonProperty("Initiator") String initiator,
            @JsonProperty("SecurityCredential") String securityCredential,
            @JsonProperty("CommandID") String commandId,
            @JsonProperty("TransactionID") String transactionId,
            @JsonProperty("PartyA") String partyA,
            @JsonProperty("IdentifierType") String identifierType,
            @JsonProperty("ResultURL") String resultUrl,
            @JsonProperty("QueueTimeOutURL") String queueTimeOutUrl,
            @JsonProperty("Remarks") String remarks,
            @JsonProperty("Occasion") String occasion
    ) {
    }

    public record AccountBalancePayload(
            @JsonProperty("Initiator") String initiator,
            @JsonProperty("SecurityCredential") String securityCredential,
            @JsonProperty("CommandID") String commandId,
            @JsonProperty("PartyA") String partyA,
            @JsonProperty("IdentifierType") String identifierType,
            @JsonProperty("ResultURL") String resultUrl,
            @JsonProperty("QueueTimeOutURL") String queueTimeOutUrl,
            @JsonProperty("Remarks") String remarks
    ) {
    }

    public record ReversalPayload(
            @JsonProperty("Initiator") String initiator,
            @JsonProperty("SecurityCredential") String securityCredential,
            @JsonProperty("CommandID") String commandId,
            @JsonProperty("TransactionID") String transactionId,
            @JsonProperty("Amount") String amount,
            @JsonProperty("ReceiverParty") String receiverParty,
            @JsonProperty("RecieverIdentifierType") String recieverIdentifierType,
            @JsonProperty("ResultURL") String resultUrl,
            @JsonProperty("QueueTimeOutURL") String queueTimeOutUrl,
            @JsonProperty("Remarks") String remarks,
            @JsonProperty("Occasion") String occasion
    ) {
    }

    public record DynamicQrPayload(
            @JsonProperty("MerchantName") String merchantName,
            @JsonProperty("RefNo") String refNo,
            @JsonProperty("Amount") String amount,
            @JsonProperty("TrxCode") String trxCode,
            @JsonProperty("CPI") String cpi,
            @JsonProperty("Size") String size
    ) {
    }
}
