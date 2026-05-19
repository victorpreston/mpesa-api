package com.mpesa_daraja_api.mpesa_daraja_api.dto.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MpesaCallbackRequest {

    @JsonProperty("TransID")
    private String transactionId;

    @JsonProperty("TransactionType")
    private String transactionType;

    @JsonProperty("TransAmount")
    private String transAmount;

    @JsonProperty("TransTime")
    private String transTime;

    @JsonProperty("BusinessShortCode")
    private String businessShortcode;

    @JsonProperty("BillRefNumber")
    private String billRefNumber;

    @JsonProperty("InvoiceNumber")
    private String invoiceNumber;

    @JsonProperty("MSISDN")
    private String msisdn;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("MiddleName")
    private String middleName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("OrgAccountBalance")
    private String orgAccountBalance;

    @JsonProperty("ThirdPartyTransID")
    private String thirdPartyTransId;

    private Map<String, Object> additionalFields = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String key, Object value) {
        additionalFields.put(key, value);
    }

    public MpesaCallbackRequest() {}

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getBusinessShortcode() {
        return businessShortcode;
    }

    public void setBusinessShortcode(String businessShortcode) {
        this.businessShortcode = businessShortcode;
    }

    public String getBillRefNumber() {
        return billRefNumber;
    }

    public void setBillRefNumber(String billRefNumber) {
        this.billRefNumber = billRefNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrgAccountBalance() {
        return orgAccountBalance;
    }

    public void setOrgAccountBalance(String orgAccountBalance) {
        this.orgAccountBalance = orgAccountBalance;
    }

    public String getThirdPartyTransId() {
        return thirdPartyTransId;
    }

    public void setThirdPartyTransId(String thirdPartyTransId) {
        this.thirdPartyTransId = thirdPartyTransId;
    }

    public Map<String, Object> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Map<String, Object> additionalFields) {
        this.additionalFields = additionalFields;
    }
}
