package com.gnetop.ltgamecommon.impl;


import com.gnetop.ltgamecommon.model.OneStoreResult;

/**
 * 是否支持支付的接口
 */
public interface onOneStoreSupportListener {

    /**
     * oneStore连接失败
     *
     * @param failedMsg 失败信息
     */
    void onOneStoreClientFailed(String failedMsg);

    /**
     * oneStore支付失败
     *
     * @param result 失败信息
     */
    void onOneStoreFailed(OneStoreResult result);

    /**
     * oneStore支付出错
     *
     * @param result 错误信息
     */
    void onOneStoreError(String result);

    /**
     * oneStore支付成功
     *
     * @param result 成功信息
     */
    void onOneStoreSuccess(OneStoreResult result);

    /**
     * oneStore连接成功
     */
    void onOneStoreConnected();

    /**
     * oneStore未连接成功
     */
    void onOneStoreDisConnected();

}
