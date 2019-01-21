package com.gnetop.ltgamecommon.impl;

public interface OnGoogleInitListener {
    /**
     * 初始化成功
     *
     * @param success 成功回调信息
     */
    void onGoogleInitSuccess(String success);

    /**
     * 初始化失败回调
     *
     * @param result 失败回调信息
     */
    void onGoogleInitFailed(String result);
}
