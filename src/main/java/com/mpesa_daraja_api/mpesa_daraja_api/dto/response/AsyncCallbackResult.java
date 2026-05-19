package com.mpesa_daraja_api.mpesa_daraja_api.dto.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AsyncCallbackResult {

    @JsonProperty("Result")
    private ResultBody result;

    @JsonProperty("Body")
    private StkResultBody body;

    private final Map<String, Object> additionalFields = new LinkedHashMap<>();

    public ResultBody getResult() { return result; }
    public void setResult(ResultBody result) { this.result = result; }

    public StkResultBody getBody() { return body; }
    public void setBody(StkResultBody body) { this.body = body; }

    @JsonAnySetter
    public void setAdditionalField(String name, Object value) { additionalFields.put(name, value); }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalFields() { return additionalFields; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResultBody {
        @JsonProperty("ResultType") private String resultType;
        @JsonProperty("ResultCode") private String resultCode;
        @JsonProperty("ResultDesc") private String resultDesc;
        @JsonProperty("OriginatorConversationID") private String originatorConversationId;
        @JsonProperty("ConversationID") private String conversationId;
        @JsonProperty("TransactionID") private String transactionId;

        public String getResultType() { return resultType; }
        public void setResultType(String resultType) { this.resultType = resultType; }
        public String getResultCode() { return resultCode; }
        public void setResultCode(String resultCode) { this.resultCode = resultCode; }
        public String getResultDesc() { return resultDesc; }
        public void setResultDesc(String resultDesc) { this.resultDesc = resultDesc; }
        public String getOriginatorConversationId() { return originatorConversationId; }
        public void setOriginatorConversationId(String originatorConversationId) { this.originatorConversationId = originatorConversationId; }
        public String getConversationId() { return conversationId; }
        public void setConversationId(String conversationId) { this.conversationId = conversationId; }
        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StkResultBody {
        @JsonProperty("stkCallback") private StkCallback stkCallback;
        public StkCallback getStkCallback() { return stkCallback; }
        public void setStkCallback(StkCallback stkCallback) { this.stkCallback = stkCallback; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StkCallback {
        @JsonProperty("MerchantRequestID") private String merchantRequestId;
        @JsonProperty("CheckoutRequestID") private String checkoutRequestId;
        @JsonProperty("ResultCode") private Integer resultCode;
        @JsonProperty("ResultDesc") private String resultDesc;

        public String getMerchantRequestId() { return merchantRequestId; }
        public void setMerchantRequestId(String merchantRequestId) { this.merchantRequestId = merchantRequestId; }
        public String getCheckoutRequestId() { return checkoutRequestId; }
        public void setCheckoutRequestId(String checkoutRequestId) { this.checkoutRequestId = checkoutRequestId; }
        public Integer getResultCode() { return resultCode; }
        public void setResultCode(Integer resultCode) { this.resultCode = resultCode; }
        public String getResultDesc() { return resultDesc; }
        public void setResultDesc(String resultDesc) { this.resultDesc = resultDesc; }
    }
}
