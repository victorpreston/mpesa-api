package com.mpesa_daraja_api.mpesa_daraja_api.enums;

public enum CommandId {

    // C2B / STK Push
    CUSTOMER_PAY_BILL_ONLINE("CustomerPayBillOnline"),
    CUSTOMER_BUY_GOODS_ONLINE("CustomerBuyGoodsOnline"),

    // B2C
    BUSINESS_PAYMENT("BusinessPayment"),
    SALARY_PAYMENT("SalaryPayment"),
    PROMOTION_PAYMENT("PromotionPayment"),

    // B2B
    BUSINESS_PAY_BILL("BusinessPayBill"),
    BUSINESS_BUY_GOODS("BusinessBuyGoods"),
    MERCHANT_TO_MERCHANT_TRANSFER("MerchantToMerchantTransfer"),
    MERCHANT_TRANSFER_FROM_MERCHANT("MerchantTransferFromMerchant"),

    // Utility
    TRANSACTION_REVERSAL("TransactionReversal"),
    TRANSACTION_STATUS_QUERY("TransactionStatusQuery"),
    ACCOUNT_BALANCE("AccountBalance");

    private final String value;

    CommandId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
