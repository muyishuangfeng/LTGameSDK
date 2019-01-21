package com.gnetop.ltgamecommon.impl;


import com.gnetop.ltgamecommon.model.BaseEntry;
import com.gnetop.ltgamecommon.model.ResultData;

/**
 * 登录回调
 */
public interface OnLoginSuccessListener {
    /**
     * 成功回调
     *
     * @param result 成功信息
     */
    void onSuccess(BaseEntry<ResultData> result);

    /**
     * 请求失败
     *
     * @param ex 失败信息
     */
    void onFailed(Throwable ex);

    /**
     * 完成时
     */
    void onComplete();

    /**
     * 参数错误
     *
     * @param result 错误信息
     */
    void onParameterError(String result);

    /**
     * 错误回调
     *
     * @param error 错误信息
     */
    void onError(String error);

}
