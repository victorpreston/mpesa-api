package com.mpesa_daraja_api.mpesa_daraja_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "mpesa")
public class DarajaProperties {

    private String consumerKey;
    private String consumerSecret;
    private String environment = "sandbox";
    private String sandboxBaseUrl = "https://sandbox.safaricom.co.ke";
    private String productionBaseUrl = "https://api.safaricom.co.ke";
    private String shortcode = "600984";
    private String lipaNaMpesaShortcode;
    private String passkey;
    private String responseType = "Completed";
    private String confirmationUrl;
    private String validationUrl;
    private String callbackUrl;
    private String timeoutUrl;
    private String resultUrl;
    private String initiatorName;
    private String securityCredential;
    private Endpoints endpoints = new Endpoints();

    public String baseUrl() {
        if ("production".equalsIgnoreCase(environment) || "live".equalsIgnoreCase(environment)) {
            return productionBaseUrl;
        }
        return sandboxBaseUrl;
    }

    public String lipaShortcodeOrDefault() {
        return StringUtils.hasText(lipaNaMpesaShortcode) ? lipaNaMpesaShortcode : shortcode;
    }

    public String callbackUrlOrDefault() {
        return StringUtils.hasText(callbackUrl) ? callbackUrl : confirmationUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getSandboxBaseUrl() {
        return sandboxBaseUrl;
    }

    public void setSandboxBaseUrl(String sandboxBaseUrl) {
        this.sandboxBaseUrl = sandboxBaseUrl;
    }

    public String getProductionBaseUrl() {
        return productionBaseUrl;
    }

    public void setProductionBaseUrl(String productionBaseUrl) {
        this.productionBaseUrl = productionBaseUrl;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getLipaNaMpesaShortcode() {
        return lipaNaMpesaShortcode;
    }

    public void setLipaNaMpesaShortcode(String lipaNaMpesaShortcode) {
        this.lipaNaMpesaShortcode = lipaNaMpesaShortcode;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getConfirmationUrl() {
        return confirmationUrl;
    }

    public void setConfirmationUrl(String confirmationUrl) {
        this.confirmationUrl = confirmationUrl;
    }

    public String getValidationUrl() {
        return validationUrl;
    }

    public void setValidationUrl(String validationUrl) {
        this.validationUrl = validationUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getTimeoutUrl() {
        return timeoutUrl;
    }

    public void setTimeoutUrl(String timeoutUrl) {
        this.timeoutUrl = timeoutUrl;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public String getSecurityCredential() {
        return securityCredential;
    }

    public void setSecurityCredential(String securityCredential) {
        this.securityCredential = securityCredential;
    }

    public Endpoints getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    public static class Endpoints {
        private String oauth = "/oauth/v1/generate?grant_type=client_credentials";
        private String stkPush = "/mpesa/stkpush/v1/processrequest";
        private String stkPushQuery = "/mpesa/stkpushquery/v1/query";
        private String c2bRegister = "/mpesa/c2b/v2/registerurl";
        private String c2bSimulate = "/mpesa/c2b/v1/simulate";
        private String b2c = "/mpesa/b2c/v3/paymentrequest";
        private String b2b = "/mpesa/b2b/v1/paymentrequest";
        private String transactionStatus = "/mpesa/transactionstatus/v1/query";
        private String accountBalance = "/mpesa/accountbalance/v1/query";
        private String reversal = "/mpesa/reversal/v1/request";
        private String dynamicQr = "/mpesa/qrcode/v1/generate";
        private String billManagerSingleInvoice = "/v1/billmanager-invoice/single-invoicing";
        private String billManagerBulkInvoice = "/v1/billmanager-invoice/bulk-invoicing";
        private String billManagerCancelInvoice = "/v1/billmanager-invoice/cancel-single-invoice";
        private String billManagerReconciliation = "/v1/billmanager-invoice/reconciliation";

        public String getOauth() {
            return oauth;
        }

        public void setOauth(String oauth) {
            this.oauth = oauth;
        }

        public String getStkPush() {
            return stkPush;
        }

        public void setStkPush(String stkPush) {
            this.stkPush = stkPush;
        }

        public String getStkPushQuery() {
            return stkPushQuery;
        }

        public void setStkPushQuery(String stkPushQuery) {
            this.stkPushQuery = stkPushQuery;
        }

        public String getC2bRegister() {
            return c2bRegister;
        }

        public void setC2bRegister(String c2bRegister) {
            this.c2bRegister = c2bRegister;
        }

        public String getC2bSimulate() {
            return c2bSimulate;
        }

        public void setC2bSimulate(String c2bSimulate) {
            this.c2bSimulate = c2bSimulate;
        }

        public String getB2c() {
            return b2c;
        }

        public void setB2c(String b2c) {
            this.b2c = b2c;
        }

        public String getB2b() {
            return b2b;
        }

        public void setB2b(String b2b) {
            this.b2b = b2b;
        }

        public String getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionStatus(String transactionStatus) {
            this.transactionStatus = transactionStatus;
        }

        public String getAccountBalance() {
            return accountBalance;
        }

        public void setAccountBalance(String accountBalance) {
            this.accountBalance = accountBalance;
        }

        public String getReversal() {
            return reversal;
        }

        public void setReversal(String reversal) {
            this.reversal = reversal;
        }

        public String getDynamicQr() {
            return dynamicQr;
        }

        public void setDynamicQr(String dynamicQr) {
            this.dynamicQr = dynamicQr;
        }

        public String getBillManagerSingleInvoice() {
            return billManagerSingleInvoice;
        }

        public void setBillManagerSingleInvoice(String billManagerSingleInvoice) {
            this.billManagerSingleInvoice = billManagerSingleInvoice;
        }

        public String getBillManagerBulkInvoice() {
            return billManagerBulkInvoice;
        }

        public void setBillManagerBulkInvoice(String billManagerBulkInvoice) {
            this.billManagerBulkInvoice = billManagerBulkInvoice;
        }

        public String getBillManagerCancelInvoice() {
            return billManagerCancelInvoice;
        }

        public void setBillManagerCancelInvoice(String billManagerCancelInvoice) {
            this.billManagerCancelInvoice = billManagerCancelInvoice;
        }

        public String getBillManagerReconciliation() {
            return billManagerReconciliation;
        }

        public void setBillManagerReconciliation(String billManagerReconciliation) {
            this.billManagerReconciliation = billManagerReconciliation;
        }
    }
}
