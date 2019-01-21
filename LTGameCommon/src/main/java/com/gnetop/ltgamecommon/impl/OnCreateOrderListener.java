package com.gnetop.ltgamecommon.impl;

public interface OnCreateOrderListener {

    void onOrderSuccess(String result);

    void onOrderFailed(Throwable ex);

    void onOrderError(String error);
}
