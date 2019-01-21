package com.gnetop.ltgamecommon.impl;


public interface OnGooglePayResultListener {
    /**
     * 支付成功
     *
     * @param result 成功信息
     */
    void onPaySuccess(String result);

    /**
     * 支付失败
     *
     * @param ex 失败信息
     */
    void onPayFailed(Throwable ex);

    /**
     * 支付完成
     */
    void onPayComplete();

    /**
     * 支付错误
     *
     * @param result 错误信息
     */
    void onPayError(String result);
}
