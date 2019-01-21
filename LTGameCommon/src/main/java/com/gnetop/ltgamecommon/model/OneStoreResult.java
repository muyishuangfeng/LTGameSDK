package com.gnetop.ltgamecommon.model;

public enum OneStoreResult {

    RESULT_PAY_OK(0, "Payment success"),
    RESULT_USER_CANCELED(1, "User cancel"),
    RESULT_SERVICE_UNAVAILABLE(2, "service unavailable"),
    RESULT_BILLING_UNAVAILABLE(3, "billing unavailable"),
    RESULT_ITEM_UNAVAILABLE(4, "item unavailable"),
    RESULT_DEVELOPER_ERROR(5, "developer error"),
    RESULT_CONNECTED_SUCCESS(6, "PurchaseClient connect success"),
    RESULT_DISCONNECT_ERROR(7, "PurchaseClient Disconnect"),
    RESULT_CONNECTED_NEED_UPDATE(8, "Connected need update"),
    RESULT_BILLING_OK(9, "Billing success"),
    RESULT_BILLING_REMOTE_ERROR(10, "Billing Remote Connection Failed"),
    RESULT_BILLING_SECURITY_ERROR(11, "Billing Apply State exceptions"),
    RESULT_BILLING_NEED_UPDATE(12, "Billing need update"),
    RESULT_PURCHASES_OK(13, "Purchases success"),
    RESULT_PURCHASES_REMOTE_ERROR(14, "Purchases Remote Connection Failed"),
    RESULT_PURCHASES_SECURITY_ERROR(15, "Purchases Apply State exceptions"),
    RESULT_PURCHASES_NEED_UPDATE(16, "Purchases need update"),
    RESULT_CONSUME_OK(17, "Consume success"),
    RESULT_CONSUME_REMOTE_ERROR(18, "Consume Remote Connection Failed"),
    RESULT_CONSUME_SECURITY_ERROR(19, "Consume Apply State exceptions"),
    RESULT_CONSUME_NEED_UPDATE(20, "Consume need update"),
    RESULT_SIGNATURE_FAILED(21, "Signature failed"),
    RESULT_PURCHASES_FLOW_OK(22, "Purchases Flow success"),
    RESULT_PURCHASES_FLOW_REMOTE_ERROR(23, "Purchases Flow Remote Connection Failed"),
    RESULT_PURCHASES_FLOW_SECURITY_ERROR(24, "Purchases Flow Apply State exceptions"),
    RESULT_PURCHASES_FLOW_NEED_UPDATE(25, "Purchases Flow need update");

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    OneStoreResult(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
