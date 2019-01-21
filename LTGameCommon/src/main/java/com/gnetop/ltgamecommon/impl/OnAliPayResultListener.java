package com.gnetop.ltgamecommon.impl;

public interface OnAliPayResultListener {

    void onAliPaySuccess(String result);

    void onAliPayFailed();

    void onAliPayError(Throwable ex);

    void onAliPayComplete();
}
