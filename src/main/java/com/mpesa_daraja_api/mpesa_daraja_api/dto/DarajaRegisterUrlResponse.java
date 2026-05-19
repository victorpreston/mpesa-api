package com.mpesa_daraja_api.mpesa_daraja_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DarajaRegisterUrlResponse {
    
    @JsonProperty("OriginatorCoversationID")
    private String originatorConversationID;
    
    @JsonProperty("ResponseCode")
    private String responseCode;
    
    @JsonProperty("ResponseDescription")
    private String responseDescription;
    
    public DarajaRegisterUrlResponse() {}
    
    public DarajaRegisterUrlResponse(String originatorConversationID, String responseCode, 
                                      String responseDescription) {
        this.originatorConversationID = originatorConversationID;
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
    }
    
    public String getOriginatorConversationID() {
        return originatorConversationID;
    }
    
    public void setOriginatorConversationID(String originatorConversationID) {
        this.originatorConversationID = originatorConversationID;
    }
    
    public String getResponseCode() {
        return responseCode;
    }
    
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    
    public String getResponseDescription() {
        return responseDescription;
    }
    
    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }
}
