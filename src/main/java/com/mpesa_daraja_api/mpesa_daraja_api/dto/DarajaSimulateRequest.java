package com.mpesa_daraja_api.mpesa_daraja_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DarajaSimulateRequest {
    
    @JsonProperty("ShortCode")
    private String shortCode;
    
    @JsonProperty("CommandID")
    private String commandID;
    
    @JsonProperty("Amount")
    private String amount;
    
    @JsonProperty("Msisdn")
    private String msisdn;
    
    @JsonProperty("BillRefNumber")
    private String billRefNumber;
    
    public DarajaSimulateRequest() {}
    
    public DarajaSimulateRequest(String shortCode, String commandID, String amount, 
                                 String msisdn, String billRefNumber) {
        this.shortCode = shortCode;
        this.commandID = commandID;
        this.amount = amount;
        this.msisdn = msisdn;
        this.billRefNumber = billRefNumber;
    }
    
    public String getShortCode() {
        return shortCode;
    }
    
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
    
    public String getCommandID() {
        return commandID;
    }
    
    public void setCommandID(String commandID) {
        this.commandID = commandID;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getMsisdn() {
        return msisdn;
    }
    
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    
    public String getBillRefNumber() {
        return billRefNumber;
    }
    
    public void setBillRefNumber(String billRefNumber) {
        this.billRefNumber = billRefNumber;
    }
}
