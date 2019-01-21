package com.gnetop.ltgamecommon.impl;

public interface OnGoogleResultListener {
    /**
     * Google play 返回信息（成功）
     *
     * @param result 成功信息
     */
    void onResultSuccess(String result);

    /**
     * 上传到服务器错误
     *
     * @param ex 错误信息
     */
    void onResultError(Throwable ex);

    /**
     * Google play 返回信息（失败）
     *
     * @param failedMsg 失败内容
     */
    void onResultFailed(String failedMsg);
}
