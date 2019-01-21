package com.gnetop.ltgamecommon.impl;

public interface OnWeChatResultListener {

    void onWeChatPaySuccess(String result);

    void onWeChatPayFailed();

    void onWeChatPayError(Throwable ex);

    void onWeChatPayComplete();
}
